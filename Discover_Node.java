import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;


public class Discover_Node{
	int node_no;
	int SERVER_PORT;
	int CLEINT_PORT;
	String HOST_NAME;
	HashMap<Integer, String> nodes_info;
	volatile Vector<Integer> nodes_known,temp_vector,resp_vector,duplicate_vector;
	volatile Vector<Integer> temp_known,closed_nodes;
	public final int MESSAGE_SIZE = 300;
	volatile int client_flag = 0,terminate = 0, server_flag= 0,terminate_final=0;
	volatile int round = 0, resp_count = 0; 
	volatile Iterator<Integer> i, it;
	volatile String message_server,message_client = "";
	volatile ByteBuffer byteBuffer_server,byteBuffer_client;
	
	public synchronized Vector<Integer> getNodes_known() {
		synchronized (nodes_known) {
			return nodes_known;
		}
		
	}

	public void setNodes_known(Vector<Integer> nodes_known) {
		this.nodes_known = nodes_known;
	}

	public synchronized int getTerminate() {
		return terminate;
	}

	public void setTerminate(int terminate) {
		this.terminate = terminate;
	}

	public Discover_Node(int node_value) throws IOException {
		
				File dir = new File(".");
				File fin = new File(dir.getCanonicalPath() + File.separator + "config_file.txt");
				
				BufferedReader br = new BufferedReader(new FileReader(fin));
				int number_of_nodes = 0; 
				String line = null;
				int counter = 0;
				
				nodes_known = new Vector<Integer>();
				nodes_info = new HashMap<Integer, String>();
				temp_vector = new Vector<Integer>();
				resp_vector = new Vector<Integer>();
				duplicate_vector = new Vector<Integer>();
				closed_nodes = new Vector<Integer>();
				
				while ((line = br.readLine()) != null ) {
					if(!(line.contains("#"))){
						
						if(line.trim().length() == 2 ){
							number_of_nodes  = Integer.parseInt(line.trim());
								//System.out.println("Number of nodes in the system  : " + number_of_nodes);
							}
						if(line.trim().length()  > 2){
								if(counter == node_value){
									String[] data = line.split("[\t]+");
									
										node_no = Integer.parseInt(data[0].trim());
										HOST_NAME = data[1].trim();
										SERVER_PORT = Integer.parseInt(data[2].trim());
										CLEINT_PORT = SERVER_PORT+10;
									
									/*	System.out.println("Node Number  : " + node_no);
										System.out.println("Host Name    : " + HOST_NAME);
										System.out.println("Server Port  : " + SERVER_PORT);*/
										
									//System.out.println(line);
								}
								if((node_value+number_of_nodes) == counter){
										
									String[] data = line.split("[\t]+");
									String values =  (String)(data[1].trim().subSequence(1, (data[1].trim().length() - 1)));
									
									
									String[] nodes = values.trim().split(",");
									
									for(int i=0 ; i<nodes.length ;i++){
										if(nodes[i].trim().length() > 0){
											nodes_known.add(Integer.parseInt(nodes[i].trim()));
										}
									}
									duplicate_vector.addAll(getNodes_known());
									//System.out.println(nodes_known);
									
									//System.out.println(line);
								} 
								if(counter < number_of_nodes && counter != node_value){
									
									String[] data = line.split("[\t]+");
									//System.out.println(line);
									nodes_info.put(Integer.parseInt(data[0].trim()), (data[1].trim()+'#'+data[2]).trim());
								
								}
								counter++;
									
							}
						}
				}
				
				System.out.println();
				
				/*Set set = nodes_info.entrySet();
				Iterator i = set.iterator();
				
				while(i.hasNext()) {
			         Map.Entry me = (Map.Entry)i.next();
			         System.out.print(me.getKey() + ": ");
			         System.out.println(me.getValue());
			      }*/
				
				br.close();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println();
		//System.out.println("================== Node " + args[0] + " Starts =====================");
		//GetConfigData(Integer.parseInt(args[0].trim()));
		Discover_Node dn = new Discover_Node(Integer.parseInt(args[0].trim()));
		dn.go();
		//System.out.println("================== Node " + args[0] + " Ends =====================");
		System.out.println("\n Closing the Application for :: " +  dn.node_no);
		System.out.println("NODES KNOWN TO NODE_NO : "+dn.node_no+ "\t IS : "+ dn.nodes_known);

	}
	
	public synchronized void addToKnownNodes(Integer value){
		synchronized (nodes_known) {
			nodes_known.add(value);
		}
		
	}
	
	public boolean isEqual(Vector<Integer> respo, Vector<Integer> dupli){
		if(respo.size() >= dupli.size()){
			Iterator<Integer> it = dupli.iterator();
			int holder;
			while(it.hasNext()){
				holder = it.next();
				if(respo.contains(holder)){
					continue;
				}else{
					return false;
				}
			}
			return true;
		}else{
			return false;
			}
		}
	
	public synchronized void msgExplore(String msg){
		String[] data = msg.split("#");
		//int value = Integer.parseInt(data[0].trim());
		
		if(!nodes_known.contains(Integer.parseInt(data[0].trim()))){
			temp_vector.add(Integer.parseInt(data[0].trim()));
			addToKnownNodes(Integer.parseInt(data[0].trim()));
			terminate = 0;
			String temp_string = (String) data[1].trim();
			//System.out.println("blah blah blah ... "+temp_string);
			String nodes[] = temp_string.split(",");
			for(int i=0 ; i <nodes.length ; i++){
				if( (!nodes_known.contains(Integer.parseInt(nodes[i].trim()))) && (Integer.parseInt(nodes[i].trim()) != node_no) ){
					addToKnownNodes(Integer.parseInt(nodes[i].trim()));
					temp_vector.add(Integer.parseInt(nodes[i].trim()));
					terminate = 0;
				}
			}
		}else if(!resp_vector.contains(Integer.parseInt(data[0].trim()))){
			resp_count++;
			//System.out.println("For node : "+node_no+" Response count : " + resp_count);
			resp_vector.add(Integer.parseInt(data[0].trim()));
			
			String temp_string = (String) data[1].trim();
			String nodes[] = temp_string.split(",");
			for(int i=0 ; i <nodes.length ; i++){
				if( (!nodes_known.contains(Integer.parseInt(nodes[i].trim()))) && (Integer.parseInt(nodes[i].trim()) != node_no) ){
					addToKnownNodes(Integer.parseInt(nodes[i].trim()));
					temp_vector.add(Integer.parseInt(nodes[i].trim()));
					terminate = 0;
				}
			}
		}
		
		if(data.length >= 3 && terminate != 0){
			if(data[2].trim().equals("closing")){
				
				
				if(!closed_nodes.contains(Integer.parseInt(data[0].trim()))){
					closed_nodes.add(Integer.parseInt(data[0].trim()));
				}
				
			if(isEqual(closed_nodes, duplicate_vector))	{
					terminate_final++;
					System.out.println("\t For Node : "+node_no+"\t Final Terminate : "+ terminate_final);
				}
			}
			
			System.out.println("For node : "+ node_no+ " Closing Vecotr: "+ closed_nodes.toString());
		}
		
		//System.out.println("For Node : "+node_no+"\t Response Vector : "+resp_vector+" \t Duplicate Vector : "+ duplicate_vector);
		
		if(isEqual(resp_vector, duplicate_vector)){
			if(temp_vector.isEmpty()){
				terminate++;
				System.out.println("\t For Node : "+node_no + "\t Terminate count : "+ terminate);
			}
			
				//nodes_known.addAll(temp_vector);
				temp_vector.removeAllElements();
				resp_vector.removeAllElements();
				duplicate_vector.removeAllElements();
				duplicate_vector.addAll(getNodes_known());
				resp_count =0;
				client_flag = 0;
			
		}
		
		
	}
	
	public void go()
	{
		
		final Thread thr1 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				//Buffer to hold messages in byte format
				byteBuffer_server = ByteBuffer.allocate(MESSAGE_SIZE);
				//String message;
				//int msg_count = 0 ;
				SctpChannel sctpChannel = null;
				
				try
				{
					//Open a server channel
					SctpServerChannel sctpServerChannel = SctpServerChannel.open();
					//Create a socket addess in the current machine at port SERVER_PORT
					InetSocketAddress serverAddr = new InetSocketAddress(SERVER_PORT);
					//Bind the channel's socket to the server in the current machine at port SERVER_PORT
					sctpServerChannel.bind(serverAddr);
					//Server goes into a permanent loop accepting connections from clients	
					
					
					System.out.println(" \n ++++++++++++ Server Started for the Node :: "+ node_no + " ++++++++++++");
					System.out.println();
					while(terminate_final < 1 && server_flag == 0)
					{	
						//Listen for a connection to be made to this socket and accept it
						//The method blocks until a connection is made
						//Returns a new SCTPChannel between the server and client
						sctpChannel = sctpServerChannel.accept();
						//Receive message in the channel (byte format) and store it in buf
						//Note: Actual message is in byte format stored in buf
						//MessageInfo has additional details of the message
						MessageInfo messageInfo = sctpChannel.receive(byteBuffer_server,null,null);
						//Just seeing what gets stored in messageInfo
						//System.out.println(messageInfo);
						//Converting bytes to string. This looks nastier than in TCP
						//So better use a function call to write once and forget it :)
						message_server = byteToString(byteBuffer_server);
						//Finally the actual message
						
						synchronized (message_server) {
							msgExplore(message_server);
						}
						
						
						System.out.println();
						System.out.println(" From "+message_server + " to node : "+ node_no);
						//System.out.println(message_server + "to node : "+ node_no+ " \t whose nodes_known : " + getNodes_known());
						//msg_count++;
						
						//System.out.println("msg count for node : "+node_no + " is : "+ msg_count);
						byteBuffer_server.clear();
						
						
					}
					
					System.out.println("\n *********** closing channel for Server node : " + node_no+" *********** with Server_ flag : "+ server_flag);
					System.out.println();
					sctpChannel.close();
					client_flag = 1 ;
				}
				catch(IOException ex)
				{	try {
					sctpChannel.close();
					} catch (IOException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(" \n ------- Error Msg while reciveing By Server Node ==> " + node_no);
					ex.printStackTrace();
					System.out.println();
				}

			}
		});
	
		
		Thread thr2 = new Thread(new Runnable() {
			
			public void run() {
			 
				//Buffer to hold messages in byte format
				byteBuffer_client = ByteBuffer.allocate(MESSAGE_SIZE);
				SctpChannel sctpChannel= null;
				
				temp_known = new Vector<Integer>();
				int j = 0 ;
				int dest_node = 80;
				//int value = 90;
				System.out.println("\n ++++++++++ Client started for the Node :: " + node_no + " +++++++++++");
				System.out.println();
				try
				{	
					while(client_flag < 2){
						synchronized (this) {
							
							temp_known.addAll(getNodes_known());
							it = temp_known.iterator();
						}
						while(it.hasNext()) 
							{
								
								int	value = (int) it.next();
								
								
						        /* System.out.print(me.getKey() + " : " + me.getValue());
						         System.out.println();*/
						
								String data = (String) nodes_info.get(value);
								String[] ServerNport  = data.trim().split("#");
								
								String ServerAddr = ServerNport[0].trim() + ".utdallas.edu";
								int ServerPort = Integer.parseInt(ServerNport[1].trim());
								dest_node= value;
								//System.out.println(" before sending value of nodes_known : " + getNodes_known());
								synchronized (message_client) {
									if(terminate >= 2){
										
											String nodes = getNodes_known().toString();
											message_client = node_no+"#"+ nodes.substring(1, nodes.length()-1)+"#closing";
									}else{
										
										String nodes = getNodes_known().toString();
										message_client = node_no+"#"+ nodes.substring(1, nodes.length()-1);
										
									 }
								}
								//System.out.println("message : " + message_client);
									
								
								
								
								//Create a socket address for  server at net01 at port 5000
								SocketAddress socketAddress = new InetSocketAddress(ServerAddr,ServerPort);
								//Open a channel. NOT SERVER CHANNEL
								sctpChannel = SctpChannel.open();
								//Bind the channel's socket to a local port. Again this is not a server bind
								j++;
								sctpChannel.bind(new InetSocketAddress(CLEINT_PORT+j));
								//Connect the channel's socket to  the remote server
								sctpChannel.connect(socketAddress);
								//Before sending messages add additional information about the message
								MessageInfo messageInfo = MessageInfo.createOutgoing(null,0);
								//convert the string message into bytes and put it in the byte buffer
								byteBuffer_client.put(message_client.getBytes());
								//Reset a pointer to point to the start of buffer 
								byteBuffer_client.flip();
								//Send a message in the channel (byte format)
								
								//if(!closed_nodes.contains(dest_node)){
									sctpChannel.send(byteBuffer_client,messageInfo);
								//}
								message_client = "";
								byteBuffer_client.clear();
								sctpChannel.close();
							}
							
							
							temp_known.removeAllElements();
							j=0;
							System.out.println("  Server Thread Alive ? == > " + thr1.isAlive());
							if(!thr1.isAlive()){
								client_flag++;
							}else{
								thr1.join(1000);
							}
							
							//while(client_flag == 1){
								try {
									System.out.println("  zzzzzzzzzzz  sleeping client node :" + node_no);
									Random rand = new Random();
									Thread.sleep(rand.nextInt(5000));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							//}
								
						
							}
						
					}
				catch(IOException ex)
				{
					server_flag = 1;
					try {
						sctpChannel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(" ----------- Error Msg while sending From  Node ==> " + node_no + " to Node : "+ dest_node);
					ex.printStackTrace();
					System.out.println();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				server_flag = 1;
				System.out.println( "\n ************ closing Client Node : "+ node_no+ " **************.... with Client _flag  :" + client_flag);
			}
		});
		
		thr1.setPriority(Thread.MIN_PRIORITY);
		thr2.setPriority(Thread.MAX_PRIORITY);
		
		thr1.start();
		
		try {
			thr1.join(4000);
			
		} catch (InterruptedException e) {
			
			System.out.println("its due to join in Server *******");
			e.printStackTrace();
			System.out.println();
		}
		
		thr2.start();
		try {
			thr2.join();
			//thr1.join();
		} catch (InterruptedException e) {
			
			System.out.println("its due to join in Server *******");
			e.printStackTrace();
			System.out.println();
		}
	
		//System.out.println(" Exiting Go Function .....FOr node  ::" + node_no);
	}

	
	public String byteToString(ByteBuffer byteBuffer)
	{
		byteBuffer.position(0);
		byteBuffer.limit(MESSAGE_SIZE);
		byte[] bufArr = new byte[byteBuffer.remaining()];
		byteBuffer.get(bufArr);
		return new String(bufArr);
	}




}

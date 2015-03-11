import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

 class Vertex {

    public int vertex;
	public Vertex mate;
    public boolean seen;
    public boolean inner,outer;
    public Vertex parent;
    public ArrayList<Edge> outgoingVertexs=  new ArrayList<Edge>();
    public ArrayList<Edge> incommingVertexs=  new ArrayList<Edge>();

   
    public Vertex(int vertex) {
        this.vertex = vertex;
        this.mate = null;
        this.parent = null;
    }
    
    public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}

	public Vertex getMate() {
		return mate;
	}

	public void setMate(Vertex mate) {
		this.mate = mate;
	}
    


    public void addOutGoingEdge( Edge newEdge){       
        outgoingVertexs.add( newEdge);       
    }
   
    public void addInCommingEdge(int edgeWeight, Edge newEdge){       
        incommingVertexs.add(newEdge);       
    }
   
    public ArrayList<Edge>  getOutGoingEdge(){       
        return outgoingVertexs;       
    }
   
    public ArrayList<Edge> getInCommingEdge(){       
        return incommingVertexs;       
    }
}


class Edge {

    public Vertex source, destination ;
    public int weight ;
   

    public  Edge(Vertex source,Vertex destination, int weight ){
        this.destination= destination;
        this.source= source;
        this.weight =weight;
    }
   
    public void setEdgeWeight(int weight){
        this.weight= weight;
    }
   
    public int getEdgeWeight(){
        return this.weight;
    }

}

public class createGraph {

    public static LinkedHashMap<Integer, Vertex> adjacency_map;
    public static HashSet<Edge> EdgeSet;
    public static TreeSet<Integer> VertexSet;
    static int msize = 0;
    static int V;
    static int E;
    static int S;

    createGraph() {

        adjacency_map = new LinkedHashMap<Integer, Vertex>();
        EdgeSet = new HashSet<Edge>();
        VertexSet = new TreeSet<Integer>();
    }


    public static createGraph createGraph() {
        Scanner scanIn = new Scanner(System.in);

        String data  = scanIn.nextLine();
        String datas[] =data.split("[ ]+");

        V = Integer.parseInt(datas[0]);
        E = Integer.parseInt(datas[1]);
       // S = Integer.parseInt(datas[2]);



        createGraph G = new createGraph();
        Vertex source, destination;


        for (int i = 0; i < E; i++) {

            data  = scanIn.nextLine();
            datas =data.split("[ ]+");

            int u =Integer.parseInt(datas[0]);
            int v=Integer.parseInt(datas[1]);


            source =adjacency_map.get(u);
            destination =adjacency_map.get(v);



            if (destination == null) {
                destination = new Vertex(v);
                adjacency_map.put(v, destination);
            }
            if (source == null) {
                source = new Vertex(u);
                adjacency_map.put(u, source);
            }

            VertexSet.add(v);
            VertexSet.add(u);
            int weight =Integer.parseInt(datas[2]);

            Edge edge = new Edge(source,destination,weight);
            EdgeSet.add(edge);


            source.addOutGoingEdge(edge);
            destination.addInCommingEdge(edge.weight,edge);


        }

        return G;

    }

    // to print the graph as input specification
    public void printGraph() 
    {
        System.out.println("Number of Vertices: " + this.V);
        System.out.println(("Number of edges: ") + this.E);
        Iterator<Entry<Integer, Vertex>> it = adjacency_map.entrySet().iterator();
        System.out.println("Adjacency List");
        while (it.hasNext()) 
        {
            Entry<Integer, Vertex> pairs = (Entry<Integer, Vertex>) it.next();
            System.out.println("Vertex Position in adj List" + pairs.getKey() + "***** Vertex Name: " + pairs.getValue().vertex);
            ArrayList<Edge> nodes = pairs.getValue().getOutGoingEdge();  
            for(int i =0 ;i < nodes.size(); i++)
            {
                Edge edge =nodes.get(i);
                System.out.println(" Edge weight  : " + edge.weight +  "   pointing toVertex : "+ edge.destination.vertex);
            } 
            System.out.println();
        }
    }

    public static void main(String[] args) {
       
       
        createGraph G = createGraph();
        long first = System.currentTimeMillis();
        G.maximumBipertite();
        long last = System.currentTimeMillis();
        //G.printGraph();
        
        System.out.println(G.msize+ "  "+ (last-first));
        if(V <= 100){
        	while(!VertexSet.isEmpty()){
        		System.out.println( VertexSet.first() +"  " +adjacency_map.get(VertexSet.first()).vertex);
        		VertexSet.remove(VertexSet.first());
        	}
        }
    }



	private void maximumBipertite() {
		// TODO Auto-generated method stub
		Vertex u,v;
		Iterator<Edge> it = EdgeSet.iterator();
		Edge edg;
		Queue<Vertex> Q = new LinkedList<Vertex>();
		
		while(it.hasNext()){
			edg = it.next();
			u = edg.source;
			v = edg.destination;
			if(u.mate == null && v.mate == null){
				u.mate = v;
				v.mate = u;
				msize++;
			}
		}
		
		TreeSet<Integer> VertexSet_1 = new TreeSet<Integer>();
		VertexSet_1 = VertexSet;
		u = adjacency_map.get(VertexSet_1.first());
		u.outer = true;
		
		while(!VertexSet_1.isEmpty()){
			if(u.outer == true){
				for(int j = 0 ; j<u.getOutGoingEdge().size() ; j++){
					v = u.getOutGoingEdge().get(j).destination;
					v.inner = true;
				}
			}else if(u.inner == true){
				for(int j = 0 ; j<u.getOutGoingEdge().size() ; j++){
					v = u.getOutGoingEdge().get(j).destination;
					v.outer = true;
				}
			}else{
				Q.add(u);
			}
			VertexSet_1.remove(VertexSet_1.first());
			
		}
		
		while(!Q.isEmpty()){
			u = Q.poll();
			
			if(u.outer == true){
				for(int j = 0 ; j<u.getOutGoingEdge().size() ; j++){
					v = u.getOutGoingEdge().get(j).destination;
					v.inner = true;
				}
			}else if(u.inner == true){
				for(int j = 0 ; j<u.getOutGoingEdge().size() ; j++){
					v = u.getOutGoingEdge().get(j).destination;
					v.outer = true;
				}
			}else{
				Q.add(u);
			}
			
		}
		
		callBack();
		
		
	}

	
	public void callBack(){
		while(true){
		boolean noAug = true;
		Queue<Vertex> Q = new LinkedList<Vertex>();
		Vertex u,v,x;
		
		TreeSet<Integer> VertexSet_1 = new TreeSet<Integer>();
		VertexSet_1 = VertexSet;


		while(!VertexSet_1.isEmpty()){
			u = adjacency_map.get(VertexSet_1.first());
			u.seen = false; u.parent = null;
			if(u.mate == null && u.outer == true){
				u.seen = true;
				Q.add(u);
			}
			VertexSet_1.remove(VertexSet_1.first());
		}
		
		while(!Q.isEmpty()){
			u = Q.poll();
			for(int j = 0 ; j<u.getOutGoingEdge().size() ; j++){
				v = u.getOutGoingEdge().get(j).destination;
				if(v.seen != true){
					v.parent = u; 	v.seen = true;
					
					if(v.mate == null){
						noAug = false;
						processAugPath(v);
						callBack();
					}
					else{
							x = v.mate;	x.seen = true;	x.parent = v;
							Q.add(x);
							
						}
					}
				}
			}
		
			if(Q.isEmpty() && noAug){
				break;
			}
		}
		
		return;
	}


	private void processAugPath(Vertex u) {
		// TODO Auto-generated method stub
		Vertex t,x,y,p;
		p = u.parent;	x = p.parent;
		u.mate = p;	p.mate = u;
		while(x != null){
			y = x.parent;	t = y.parent;
			x.mate = y;	y.mate = x;
			x = t;
		}
		msize++;
	}

}

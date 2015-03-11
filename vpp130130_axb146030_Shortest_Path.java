/* Abhishek Bansal (axb146030)
 * Vinay Parekar (vpp130130)
 * 
 * @author Abhishek Bansal,Vinay Parakar
 * @email-id axb146030@utdallas.edu, vpp130130@utdallas.edu
 * @version 1.0
 * 
 * This project mainly focuses on Implementing Breadth First Search (BFS), Dijkstra's algorithm, DAG shortest paths, and Bellman-Ford Shortest Path algorithm 
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

/*
 * The class Vertex stores the Vertex of the Graph
 * @Variable : Vertex is vertex number of vertex.
 * @Variable : dist is the distance from source to the Vertex.
 * @Variable : Count is used to detect cycle in Bellman Ford Algorithm
 * @Variable : pred is the predecessor of the Vertex
 * @Variable : color is used in DAG
 * @Variable : top is used for Topological order
 * @Variable : outgoingEdges store the list of outgoing edges from that vertex.  
 * @Variable : incomingEdges store the list of incoming edges to that vertex.  
 */

 class Vertex implements Comparable<Vertex> 
 {
    public long vertex;
    public long dist = Integer.MAX_VALUE;
    public long count = 0;
    public Vertex pred = null;
    public String color = "white";
    public long top = Long.MAX_VALUE;
    public ArrayList<Edge> outgoingEdges=  new ArrayList<Edge>();
    public ArrayList<Edge> incommingEdges=  new ArrayList<Edge>();
    
    /*
     * Constructor for Initialization
     */
    
    public Vertex(long vertex) 
    {
        this.vertex = vertex;
        this.count = 0;
        this.dist = Integer.MAX_VALUE;
        this.pred = null;
        this.color = "white";
        this.top = Long.MAX_VALUE;
    }

    /*
     * addOutgoingEdge is used to add outgoing edges to the vertex
     * Add the edge in the list of outgoingedges.
     */
    
    public void addOutGoingEdge( Edge newEdge)
    {       
        outgoingEdges.add( newEdge);       
    }
   
    /*
     * addIncomingEdge is used to add incoming edges to the vertex
     * Add the edge in the list of incoming edges.
     */
    
    public void addInCommingEdge(long edgeWeight, Edge newEdge)
    {       
        incommingEdges.add(newEdge);       
    }
   
    /*
     * getOutGoingEdge is used to return all outging edges
     * returns all outgoing edges
     */
    
    public ArrayList<Edge> getOutGoingEdge()
    {       
        return outgoingEdges;       
    }
    
    /*
     * getInComingEdges is used to return all incoming edges
     * return all incoming edges
     */
   
    public ArrayList<Edge> getInCommingEdge()
    {       
        return incommingEdges;       
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * used in storing vertex in Heap
     */
    
	public int compareTo(Vertex o) 
	{
		if(dist>o.dist)
			return 1;
		else if(dist<o.dist)
			return -1;
		else
			return 0;
	}
}

 /*
  * The class Edge stores the Edges of the Graph
  * @Variable : Source, Destination is used to store source and destination vertex.
  * @Variable : weight is the weight of the edge
  */ 
 
class Edge 
{
    public Vertex source, destination ;
    public long weight ;

    /*
     * Constructor used to initialize the values
     */
    
    public  Edge(Vertex source,Vertex destination, long weight )
    {
        this.destination= destination;
        this.source= source;
        this.weight =weight;
    }
   
    /*
     * SetEdgeWeight is used to set Weight of the Edge
     */
    
    public void setEdgeWeight(long weight)
    {
        this.weight= weight;
    }
   
    /*
     * getEdgeWeight is used to return the weight of the Edge
     */
    
    public long getEdgeWeight()
    {
        return this.weight;
    }
}

/*
 *  Graph Class to store whole graph
 *  adjacency_map to map node number to node of the graph
 *  BFS_flag,Djk_flag,BF_flag,DAG_flag flag set run algorithm according
 *  source and destination vertex stores source and destination of edge
 *  V - number of vertices/nodes in the graph
 *  E - number of edges in the graph
 *  S - source node in the graph
 *  T - Target node in the graph
 *  uniform_wt - uniform weight of all the edges
 *  topNum - count to store topological oder of nodes
 *  topo_order_map - stores node index in the topological order
*/
public class vpp130130_axb146030_Shortest_Path 
{
    public static LinkedHashMap<Long, Vertex> adjacency_map;
    static boolean BFS_flag=true,Djk_flag=true,BF_flag=true,DAG_flag=true;
    public static Vertex source, destination;
    static long V;
    static long E;
    static long S;
    static long T;
    static long uniform_wt;
	static long topNum = V;
	static TreeMap<Long, Vertex> topo_order_map= new TreeMap<Long, Vertex>();
    
	/*
	 * constructor sets a linkedHashMap to map vertex to vertex value
	*/
	vpp130130_axb146030_Shortest_Path() 
    {
        adjacency_map = new LinkedHashMap<Long, Vertex>();
    }

	/*
	 * creates a graph
	 * 
	*/
    public static vpp130130_axb146030_Shortest_Path createGraph() 
    { 
        Scanner scanIn = new Scanner(System.in);
        String data  = scanIn.nextLine();
        String datas[] =data.split("[ ]+");
        V = Long.parseLong(datas[0]);		// sets number of vertices
        E = Long.parseLong(datas[1]);		// sets numeber of edges
        S = Long.parseLong(datas[2]);		// sets source Vertex
        T = Long.parseLong(datas[3]);		// sets target Vertes
        vpp130130_axb146030_Shortest_Path G = new vpp130130_axb146030_Shortest_Path();	// create an empty graph
        
        //sets adjacency_map with vertex number to  vertex entry
        for(long i = 1; i<= V ; i++ )
        {
        	adjacency_map.put(i, new Vertex(i));
        }
        
        long old_wt = 0;   				// stores  weight of preveious iteration
        
        // retrives input entry line by line and splits it according to spaces 
        for (long i = 0; i < E; i++) 
        {
            data  = scanIn.nextLine();
            datas =data.split("[ ]+");
            long u =Long.parseLong(datas[0]);
            long v= Long.parseLong(datas[1]);
            long weight =Long.parseLong(datas[2]);
           
            // intialize the old_wt and uniform_wt parameters
            if(i == 0)
            {
            	old_wt = weight;
            	uniform_wt = weight;
            }
            
            // check if it satisfies condition BFS shortest path algorithm with non-negative and uniform weight
            if(old_wt != weight || weight < 0)
            {
            	BFS_flag = false;
            }
            
            // check if it satisfies condition Dijkstra's shortest path algorithm with non-negative
            if(weight < 0)
            {
            	Djk_flag = false;
            }
            
            old_wt = weight; 				// set current  weight to old_wt
            source =adjacency_map.get(u);	// set source node of the edge
            destination =adjacency_map.get(v);		// sets destination of the edge
            
            // if source node is not set yet
            if (source == null) 
            {
                source = new Vertex(u);
                adjacency_map.put(u, source);
            }
            
            // if destination node is not set yet
            if (destination == null) 
            {
                destination = new Vertex(v);
                adjacency_map.put(v, destination);
            }
            Edge edge = new Edge(source,destination,weight);  // set edge
            source.addOutGoingEdge(edge);					// add the edge to vertex adjecency list of source node 
        }
        return G;
    }

    
    // to print the graph as input specification
    public void printGraph() 
    {
        System.out.println("Number of Vertices: " + this.V);
        System.out.println(("Number of edges: ") + this.E);
        Iterator<Entry<Long, Vertex>> it = adjacency_map.entrySet().iterator();
        System.out.println("Adjacency List");
        while (it.hasNext()) 
        {
            Entry<Long, Vertex> pairs = (Entry<Long, Vertex>) it.next();
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

    /*
     * main function
     *  
    */
    public static void main(String[] args) 
    {
    	vpp130130_axb146030_Shortest_Path G = createGraph();				// create a graph a G
    	long first = System.currentTimeMillis();
    	G.initialize();								// intialize the graph
    	G.createTopologicalOrder(S);				// to create a topological odering and set DAG flag
    	
    	if(BFS_flag)
    	{
    		System.out.print("BFS  ");
    		G.find_BFS_ShortPath();				// FInds the Shortest path using BFS algorithm 
    	}
    	
    	else if(DAG_flag )
    	{
    		System.out.print("DAG  ");
    		G.find_DAGShortestPath();			// Finds the shortest path using DAG shortest path algorithm
    	}
    	
    	else if(Djk_flag && !DAG_flag && !BFS_flag)
    	{
    		System.out.print("Djk  ");
    		G.find_Dijkstra_ShortestPath();		// Finds the Shortest path using Dijkstra's shortest path algorithm
    	}
    	
    	if(!BFS_flag && !DAG_flag && !Djk_flag)
    	{
    		System.out.print("B-F  ");
    		G.find_BFShortestPath();			// Finds the shortest path using Bellman-Ford Algorithm
    	}
    	
    	long last = System.currentTimeMillis();
    	System.out.print( vpp130130_axb146030_Shortest_Path.adjacency_map.get(vpp130130_axb146030_Shortest_Path.T).dist+ "  "+(last-first) + "\n");
    	
    	if(V < 100)
    	{
    		G.print_ShortestPath();
    	}
        
    	//G.printGraph();
    }

    /*
     * implements Dijksatrs's algorithm to find shortest path 
     * Heap to store vertices with respect to their distance value
    */
    private void find_Dijkstra_ShortestPath() 
    {
		PriorityQueue<Vertex> Heap = new PriorityQueue<Vertex>();		// Heap created
		
		// sets heap with all the vertices
		for(long i=1 ; i<= V ; i++)
		{
			Heap.add(adjacency_map.get(i));		
		}
		
		Vertex u,v;
		long dist_value;
		
		// runs while loop until heap is not empty
		while(!Heap.isEmpty())
		{
			u = Heap.poll(); 		// pull out the vertex with least dist value 
			
			// runs for loop for each adjecent value node of the vertex u 
			for(long i = 0; i < u.getOutGoingEdge().size() ; i++)
			{
				v = u.getOutGoingEdge().get((int)i).destination;
				dist_value = relax(u.getOutGoingEdge().get((int)i));
				
				// check if the distance value is being changed or not 
				if(dist_value != v.dist)
				{
					Heap.remove(v);
					Heap.add(adjacency_map.get(v.vertex));
				}
			}
			
		}
		
	}
	
    /*
     * BFS algorithm to get shortest path 
     * Q is the queue to store vertices
     * 
    */
	private void find_BFS_ShortPath() 
	{
		Queue<Long> Q = new LinkedList<Long>();
		Q.add(S);					// add the source node in the queue
		Vertex u,v;
		
		//while Q is not empty do
		while(!Q.isEmpty())
		{
			u = adjacency_map.get(Q.poll());		// pulls out the node from Q
			
			//runs for loop for each adjecent value node of the vertex u 
			for(long i = 0; i < u.getOutGoingEdge().size() ; i++)
			{
				v = u.getOutGoingEdge().get((int)i).destination;
				
				// check if the distance value is being changed or not 
				if(v.dist ==Integer.MAX_VALUE)
				{
					v.dist = u.dist + uniform_wt;			// set the dist value for the node
					v.pred = u;								// set parent pointer for the node
					Q.add(v.vertex);						// add the vertex in the Q
				}
			}
		}
	}

	
	/*
	 * DAG algorithm to find the shortest path 
	 * uses the created topological order
	*/
	public void find_DAGShortestPath() 
	{
		Vertex u,v;
		DAG_flag = true;		// set the DAG_flag 
		
		//runs the loop according to topological order
		for(long i = topo_order_map.firstKey(); i <= topo_order_map.lastKey() ; i++)
		{
			u = topo_order_map.get(i);				// get vertex from the topological order
			
			//runs for loop for each adjecent value node of the vertex u 
			for(long j = 0 ; j < u.getOutGoingEdge().size() ; j++)
			{
				relax(u.getOutGoingEdge().get((int)j));			// relax edge
			}
		}
	}

	/*
	 * createTopologicalOrder creates topological order and sets DAG_flag
	 * 
	*/
	private void createTopologicalOrder(long v1) 
	{
		Vertex v,u;
		u = adjacency_map.get(v1);
		u.color = "gray";			// node under consideration

		//runs for loop for each adjecent value node of the vertex u 
		for(long i = 0 ; i < u.getOutGoingEdge().size() ; i++)
		{
			v = u.getOutGoingEdge().get((int)i).destination;
			
			// nodes that are not yet processed
			if(v.color.equals("white"))
			{
				v.pred = u;
				createTopologicalOrder(v.vertex);
			}
			if(v.color.equals("gray"))// nodes that are already processed to find the loop 
			{
				DAG_flag = false;
			}
		}
		topo_order_map.put(topNum,u);		// add vertex in the topological order
		u.top = topNum--;			
		u.color = "Black";			// node that are done processing with
	}

	/*
	 * prints the shortest path from each vertex with its predecessor
	*/
	public void print_ShortestPath() 
	{
		Vertex u;
		
		// runs loop for all the verices
		for(long i =1; i <= V ; i++)
		{
			
			u = adjacency_map.get(i);
			if(u.dist == Integer.MAX_VALUE && u.pred == null )
			{
				System.out.println(i + "\tINF\t-");
			}
			if(u.pred == null && u.dist != Integer.MAX_VALUE)
			{
				System.out.println(i + "\t" + u.dist + "\t-");
			}
			if(u.pred != null && u.dist != Integer.MAX_VALUE)
			{
				System.out.println(i + "\t" +u.dist+"\t"+u.pred.vertex);
			}
		}
	}

	/*
	 * runs Bellman-Ford algorithm to find the shortest path 
	 * Q sets queue
	 * 
	*/
	public void find_BFShortestPath() 
	{
		Queue<Long> Q = new LinkedList<Long>();
		Q.add(S);
		Vertex u;
		
		// while queue is not empty do
		while(!Q.isEmpty())
		{
			u = adjacency_map.get(Q.poll());
			
			// checks if the -ve cycle exitst or not
			if(u.count++ > V )
			{
				System.out.println("Booyah!!");
				System.exit(0);
			}
			ArrayList<Edge> u_edges= u.getOutGoingEdge();
			for(int i = 0; i < u_edges.size()  ; i++)
			{
				long old_dist = relax(u_edges.get(i));
				if(old_dist != u_edges.get(i).destination.dist && ! Q.contains(u_edges.get(i).destination) )
				{
					Q.add(u_edges.get(i).destination.vertex);
				}
			}
		}	
	}

	/*
 * to relax edge
 * 
	*/
	public long relax(Edge edge) 
	{
		long old_dist = edge.destination.dist;
		if(edge.destination.dist > edge.source.dist + edge.weight)
		{
			edge.destination.dist = edge.source.dist + edge.weight;
			edge.destination.pred = edge.source;
		}
		return old_dist;
	}

	/* initializes the  graph
	*/
	public void initialize() 
	{
		adjacency_map.get(S).dist = 0;
	}
}
	



import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

 class Vertex {

    public int vertex;
	public int heap_index;
	public boolean inS;
	public int dist = Integer.MAX_VALUE;
	public Vertex parent = null;
	
	
	public ArrayList<Edge> edges=  new ArrayList<Edge>();
    
	// constructor
	public Vertex(int vertex) {
        this.vertex = vertex;
    }

	
    // getter and setter method for Vertex
    public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	
	
	
	// getter and setter method for Heap_index 
	public int getHeap_index() {
		return heap_index;
	}

	public void setHeap_index(int heap_index) {
		this.heap_index = heap_index;
	}


	
	// getter and setter method for edges
    public ArrayList<Edge> getEdges() {
		return edges;
	}


	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	
	// getter and setter mothod for inS

	public boolean isInS() {
		return inS;
	}


	public void setInS(boolean inS) {
		this.inS = inS;
	}

	// getter and setter method for parent
	public Vertex getParent() {
		return parent;
	}


	public void setParent(Vertex parent) {
		this.parent = parent;
	}


	// to Add connected edge to the vertex 
	  public void addEdge( Edge newEdge){       
	        edges.add( newEdge);       
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

public class Graph {

    static int V;		// number of vertices
    static int E;		// number of Edges
    static int S;		// root node index
    Vertex root;		//root vertex
    
    public static LinkedHashMap<Integer, Vertex> adjacency_map;		// Hash map for vertex and it's index value

    // Constructor
    Graph() {

        adjacency_map = new LinkedHashMap<Integer, Vertex>();

    }



    public static Graph createGraph() {
        Scanner scanIn = new Scanner(System.in);

       // String data  = scanIn.nextLine();
       // String datas[] =data.split("[ ]+");

        V = Integer.parseInt(scanIn.next().trim());
        E = Integer.parseInt(scanIn.next().trim());

        //System.out.println("V : "+ V + "E :"+ E);

        Graph G = new Graph();
        Vertex source, destination;


        for (int i = 0; i < E; i++) {

           // data  = scanIn.next();
            //datas =data.split("\\s*[ ]+|\\s*|[ ]+| ");

            int u =Integer.parseInt(scanIn.next().trim());
            int v=Integer.parseInt(scanIn.next().trim());

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

            int weight =Integer.parseInt(scanIn.next().trim());

            Edge edge = new Edge(source,destination,weight);
            source.addEdge(edge);
            
            edge = new Edge(destination,source,weight);
            destination.addEdge(edge);

        }

        return G;

    }

    public void printGraph() {
        System.out.println("Number of Vertices: " + this.V);
        System.out.println(("Number of edges: ") + this.E);
        Iterator<Entry<Integer, Vertex>> it = adjacency_map.entrySet().iterator();
        System.out.println("Adjacency List");
        while (it.hasNext()) {
            Entry<Integer, Vertex> pairs = (Entry<Integer, Vertex>) it.next();

            System.out.println("Vertex Position in adj List" + pairs.getKey() + "***** Vertex Name: " + pairs.getValue().vertex);
            ArrayList<Edge> nodes = pairs.getValue().getEdges();
           // Iterator<Integer> edgesList = nodes.keySet().iterator();   

            for(int i =0 ;i < nodes.size(); i++){

                Edge edge =nodes.get(i);

                System.out.println(" Edge weight  : " + edge.weight +  "   pointing toVertex : "+ edge.destination.vertex);

            }   

            System.out.println();
        }
    }
    
	public int createMST() {
		int mst_weight = 0;
		root = adjacency_map.get(1);
		root.dist = 0;			 // setting root distance to Zero
		
		//Creating a MinHeap 'Heap'
		PriorityQueue<Vertex> Heap = new PriorityQueue<Vertex>(V-1,DistComparator);	
		for(int i=1 ; i<= V ; i++){
			Heap.offer(adjacency_map.get(i));		
		}
		
		Vertex[] v_array = new Vertex[V-1];
		v_array = Heap.toArray(v_array);
		
		// Setting up Heap Index from 0 to V-1
		for(int i = 0 ; i < V ;i++){
			v_array[i].heap_index = i;
		}
		
		Vertex u,v;
		while( ! Heap.isEmpty()){
			u = Heap.poll();
			u.inS = true;
			for(int i = 0; i < u.edges.size(); i++){
				Edge graph_edge = u.edges.get(i);
				v = graph_edge.destination;
				if((!v.isInS()) && graph_edge.weight < v.dist){
					v.parent = u;
					v.dist = graph_edge.weight;
					
					//decreaseKey(Heap,v.heap_index,v.dist);
					Heap.remove(v);
					Heap.offer(v);
					
					//v_array = Heap.toArray(v_array);
				/*	for(int j= 0 ; j < v_array.length ;j++){
						System.out.print(v_array[j].dist+"  ");
						
					}
					System.out.println();
					System.out.println();*/
					
					
				}
				
			}
		}
		Vertex x;
		for(int i=1 ; i<=V ; i++){
			 x= adjacency_map.get(i);
			 mst_weight =mst_weight + x.dist;
		}
		
		return mst_weight;
	}
	
	public void printMST(){
		Vertex x;
		int num_of_edges = 0;
		for(int i=2; i<= V ; i++){
			 x= adjacency_map.get(i);
			 if( V < 100){
				 System.out.println("("+ x.vertex +","+x.parent.vertex+")");
			 }

		}
		
	}
    public static void main(String[] args) {
       
       
        Graph G = createGraph();
       // G.printGraph();
        
        Long first = System.currentTimeMillis();
        int mst_weight = G.createMST(); 
        System.out.print(mst_weight+ "");
        Long last = System.currentTimeMillis();
        System.out.println((last - first));
       
        G.printMST();
    }


    public static Comparator<Vertex> DistComparator = new Comparator<Vertex>() {
        @Override
    	public int compare(Vertex v1, Vertex v2) {
            return (int) (v1.dist - v2.dist);
        }
        
    };


}

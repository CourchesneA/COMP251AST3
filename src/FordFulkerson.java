import java.io.*;
import java.util.*;




public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		
		//MY Code
		
		//I would like to use recursion and I need an external data structure to
		//keep track of the visited nodes. Since I have to write the code only in here,
		// I will use recursion ont his method and pass a smaller graph as a parameter.
		// Since we dont have access to proper set methods on graphs but we can clone a graph,
		// I will clone the graph and set edges to 0 to have kind of a sub graph
		
		
		if (source == destination){
			Stack.add(source);
			return Stack;	//Base case
		}
		
		WGraph subGraph = new WGraph(graph);	//Since we can't keep track of visited nodes, use subgraphs
		for(Edge e:subGraph.getEdges()){
			if((e.nodes[0] == source || e.nodes[1] == source) && e.weight != 0){
				subGraph.setEdge(e.nodes[0], e.nodes[1], 0);
			}
		}
		
		for(Edge e:graph.getEdges()){
			if(e.nodes[0] == source && e.weight != 0){
				ArrayList<Integer> path = pathDFS(e.nodes[1],destination,subGraph);
				if(path.size() != 0){
					Stack.add(source);
					Stack.addAll(path);
					return Stack;
				}
			}
		}
		
		//My Code ^
		
		return Stack;
	}
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260688650"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		
				// YOUR CODE GOES HERE
		//Compute max flow and graph
		
		//Find a path using a naive algorithm (DFS) with flow at bottleneck value
		
		ArrayList<Integer> path = pathDFS(source, destination, graph);
		//Find bottleneck
		int bottleneck = graph.getEdge(path.get(0), path.get(1)).weight;	// init weight as first weight
		for(int i=0; i< path.size()-1; i++){			//Find bottleneck
			if(bottleneck > graph.getEdge(path.get(i), path.get(i+1)).weight){		//TODO smallest residual capacity ?
				bottleneck = graph.getEdge(path.get(i), path.get(i+1)).weight;
			}
		}
		//Create flow graph with 0s and Bottleneck for the path
		WGraph flow = new WGraph(graph);
		for(Edge e: graph.getEdges()){
			if(path.indexOf(e.nodes[1]) == path.indexOf(e.nodes[0])+1){ //edge exists on path
				flow.setEdge(e.nodes[0], e.nodes[1], bottleneck);
			}else{
				flow.setEdge(e.nodes[0], e.nodes[1], 0);
			}
		}
		 
		//Compute residual graph
		// clone graph and
		// for each edge in DFS path
		// add backward edge of flow value
		// subtract flow from edge
		WGraph residual = new WGraph();
		for(Edge e: flow.getEdges()){
			//add backward edge of flow value
			residual.addEdge(new Edge(e.nodes[1], e.nodes[0], e.weight));
		}
		for(Edge e: graph.getEdges()){
			//add a edge of graph-flow
			residual.addEdge(new Edge(e.nodes[0], e.nodes[1], e.weight - residual.getEdge(e.nodes[0], e.nodes[1]).weight));
		}
				
		//TODO Augment graph
		
		while((path = pathDFS(source, destination, residual)).size() != 0){	//while there is a s-t path in residual
		//flow.augment(P)
			//find bottleneck
			bottleneck = residual.getEdge(path.get(0), path.get(1)).weight;	// init weight as first weight
			for(int i=0; i< path.size()-1; i++){			//Find bottleneck
				if(bottleneck > residual.getEdge(path.get(i), path.get(i+1)).weight){		//TODO smallest residual capacity ?
					bottleneck = residual.getEdge(path.get(i), path.get(i+1)).weight;
				}
			}
			
			//Augment
			for(int i=0; i< path.size()-1; i++){			//Foreach edge on the path
				int node1 = path.get(i);
				int node2 = path.get(i+1);
				
				//Augment the flow
				//Add bottleneck to each edge on the path
				Edge fe = flow.getEdge(node1, node2);
				Edge be = flow.getEdge(node2, node1);
				
				if(fe!= null && (be== null || be.weight == 0)){	//forward edge
					flow.setEdge(node1, node2, flow.getEdge(node1, node2).weight + bottleneck);
				}else{
					//Backward edge
					flow.setEdge(node1, node2, flow.getEdge(node1, node2).weight - bottleneck);
				}			
			}	
			
		//Update residual based on new flow
			for(Edge e: flow.getEdges()){
				//add backward edge of flow value
				residual.addEdge(new Edge(e.nodes[1], e.nodes[0], e.weight));
			}
			for(Edge e: graph.getEdges()){
				//add a edge of graph-flow
				residual.addEdge(new Edge(e.nodes[0], e.nodes[1], e.weight - residual.getEdge(e.nodes[0], e.nodes[1]).weight));
			}
		}
		graph = flow;
		//Compute the max flow
		for(Edge e:graph.getEdges()){
			if(e.nodes[1] == destination){
				maxFlow+=e.weight;
			}
		}
		
		//******************
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
		 //System.out.println(pathDFS(g.getSource(), g.getDestination(), g));
	 }
}

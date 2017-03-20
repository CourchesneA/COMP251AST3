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
				if(path != null){
					Stack.add(source);
					Stack.addAll(path);
					return Stack;
				}
			}
		}
		
		//My Code ^
		
		return Stack;
	}
	
//	public static void dfs(Integer source, Integer destination, WGraph graph, int[] visited){
//		ArrayList<Integer> stack = new ArrayList<Integer>();
//		//Take source node
//		//Check if its destination
//		if(source.equals(destination)){
//			//We found the path s->d, return the stack
//			Stack.add(destination);
//			return Stack;
//		}
//		
//		//Add neighbors on the stack
//		for(Edge e:graph.getEdges()){
//			if(e.nodes[0] == source){
//				Stack.add(e.nodes[1]);
//			}
//		}
//		
//		//mark as visited
//		//Goto last node on the stack
//	}
	
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260688650"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		
				/* YOUR CODE GOES HERE
		//
		//
		//
		//
		//
		//
		//
		*/
		
		
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
		 //fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
		 System.out.println(pathDFS(g.getSource(), g.getDestination(), g));
	 }
}

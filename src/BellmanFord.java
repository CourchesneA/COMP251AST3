import java.util.*;

public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws Exception{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         */
        
        /* YOUR CODE GOES HERE */
    	source = g.getSource();
    	distances = new int[g.getNbNodes()];
    	java.util.Arrays.fill(distances, Integer.MAX_VALUE);
    	distances[0] = 0;
    	
    	predecessors = new int[g.getNbNodes()];
    	java.util.Arrays.fill(predecessors, -1);
    	
    	
    	for(int i=0; i< g.getNbNodes(); i++){
    		for(Edge e:g.getEdges()){
    			if(distances[e.nodes[0]] == Integer.MAX_VALUE){
    				continue; //We are not going to relax anything if we cant reach first node
    			}
    			int newDistance = distances[e.nodes[0]]+ e.weight;
    	    	if(distances[e.nodes[1]] > newDistance){
    	    		distances[e.nodes[1]] = newDistance;
    	    		predecessors[e.nodes[1]] = e.nodes[0];
    	    	}

    		}
    	}
    	//After |V|-1 iteration, if not done then there is a negative cycle
//    	for(Edge e:g.getEdges()){
//    		if(distances[e.nodes[1]] > distances[e.nodes[0]] + g.getEdge(e.nodes[0],e.nodes[1]).weight){
//    			//return false
//    		}
//    	}
    	//return true
    	
    	
    	
    }
    
 

    public int[] shortestPath(int destination) throws Exception{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */
    	
    	//check if there is a path
    	if(distances[destination] == Integer.MAX_VALUE){
    		throw new Exception("No path to the destination exist");
    	}
    	
    	//There is a path, return it
    	java.util.ArrayList<Integer> pathAL = new ArrayList<Integer>();
    	int currentNode = destination;
    	while (currentNode != source){
    		pathAL.add(0, currentNode);
    		currentNode = predecessors[currentNode];
    	}
    	pathAL.add(0,source);
    	
    	int[] path = new int[pathAL.size()];
    	for(int i=0; i<pathAL.size(); i++){
    		path[i] = pathAL.get(i);
    	} 
        
        return path;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}

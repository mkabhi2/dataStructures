import java.util.*;

public class TarjanSCC {
    private int time = 0;
    private int[] ids, low; //Store node ids and their LLV
    private boolean[] onStack;
    private Stack<Integer> stack;
    private List<List<Integer>> sccs;
    private List<List<Integer>> graph; // adjacency list

    public TarjanSCC(List<List<Integer>> graph) {
        int totalVertices = graph.size();
        this.graph = graph;
      
        ids = new int[totalVertices]; //Store node ids
        Arrays.fill(ids, -1); //Default value
        low = new int[totalVertices]; //Store node's LLV
        onStack = new boolean[totalVertices]; //array that signifies whether an element is on stack or not
        stack = new Stack<>(); //the actual stack
        sccs = new ArrayList<>(); //SCC list to be returned
        for (int i = 0; i < n; i++)
            if (ids[i] == -1)
                dfs(i);
    }

    private void dfs(int at) {
        ids[at] = low[at] = time++;   //Set node id and its LLV same and equal to a monotonically increasing value
        stack.push(at);               //Push the node to stack
        onStack[at] = true;           //Mark that the node has been pushed to stack

        //Iterate over all neighbours of the current node
        for (int to : graph.get(at)) {
          
            if (ids[to] == -1) {      //If the neighbour is globally unvisited yet, that means it would not be assigned any id too
                dfs(to);              //Explore the neighbour in DFS
                low[at] = Math.min(low[at], low[to]);    //Update LLV value to the minimum of current node LLV or the LLV of the neighbour once it has been visited
            } else if (onStack[to]) {    //If the neighbour has been visited already and is present on the stack as well, that means this is a backedge, so update the LLV of current node a bit differently
                low[at] = Math.min(low[at], ids[to]);    //update LLV to the minimum of current node LLV or the Node Id of the ancestral node the backedge points to. Why not LLV value of the ancestor?
                                                        //Because LLV of the ancestral node might would have changed, and using its LLV might point us to another ancestral edge
            }
        }

        // After exploring all the neighbours of the current node, if the NodeId and LLV of the current node are same then it means we have found start of a strongly connected component
        if (ids[at] == low[at]) {
            List<Integer> scc = new ArrayList<>();

            //pop nodes from stack, mark them as not present in stack and add them to the newly found SCC nodes list, until the current node is popped from the stack
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                scc.add(node);
                if (node == at) break;
            }

            //Add the newly found SCC node list to the list of SCCs.
            sccs.add(scc);
        }
    }

    
    public List<List<Integer>> getSCCs() {
        return sccs;
    }
    
    // Example usage
    public static void main(String[] args) {

        //Graph intialalization starts
      
        int n = 5;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        // add edges: graph.get(from).add(to);
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);
        graph.get(1).add(3);
        graph.get(3).add(4);

        //Graph initialisation ends

      
        TarjanSCC tarjan = new TarjanSCC(graph);
        List<List<Integer>> sccs = tarjan.getSCCs();

      
        System.out.println("Strongly Connected Components:");
        for (List<Integer> scc : sccs) {
            System.out.println(scc);
        }
    }
}

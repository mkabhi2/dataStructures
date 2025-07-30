import java.util.*;

public class TarjanBCC {
    private int n, time;
    private List<List<Integer>> graph;
    private int[] disc, low, parent;
    private boolean[] articulationPoint;
    private Stack<Edge> edgeStack;
    private List<List<Edge>> biconnectedComponents;

    // Represents an undirected edge
    static class Edge {
        int u, v;
        Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
        public String toString() {
            return "(" + u + ", " + v + ")";
        }
    }

    public TarjanBCC(List<List<Integer>> graph) {
        this.graph = graph;
        this.n = graph.size();
        time = 0;
        disc = new int[n];
        low = new int[n];
        parent = new int[n];   //To keep track of the parent of each node
        articulationPoint = new boolean[n];  //Store articualtion points
        Arrays.fill(disc, -1);
        Arrays.fill(parent, -1);
        edgeStack = new Stack<>(); //Store edges in a stack
        biconnectedComponents = new ArrayList<>();   //Store BiConnedted Component Edges

        for (int i = 0; i < n; i++)
            if (disc[i] == -1)
                dfs(i);
    }

    private void dfs(int u) {
      
        disc[u] = low[u] = ++time;    //Assign ID and LLV to the current node
        int children = 0;             //Keep track of number of neighbours for a specific condition only -> If the current node is root node and has more than 1 Children 
                                      //then it is an articulation point

        for (int v : graph.get(u)) {      //Iterate over all neoighbours
            if (disc[v] == -1) {          //Perfrom DFS on the neighbour only if its unvisited yet
                children++;                //Increase child count -> Unexplore neighbour
                parent[v] = u;            //Mark the current node as the parent of the neighbour
                edgeStack.push(new Edge(u, v));      //Push an (u,v) to stack

                dfs(v);                    //perform DFS on the neighbour

                low[u] = Math.min(low[u], low[v]);        //Calculate the lowest LLV for the current node and the neighbours

                // Condition for articulation point and BCC
                if ((parent[u] == -1 && children > 1) ||              //If node is root node with more than 1 child or 
                    (parent[u] != -1 && low[v] >= disc[u])) {          //there is no earlier node reachable from the current node

                    articulationPoint[u] = true;                      //articulation point found
                    // Start a new BCC
                    List<Edge> bcc = new ArrayList<>();
                    Edge e;
                    do {
                        e = edgeStack.pop();                        //return a bcc adding edges from stack until (u,v) edge found
                        bcc.add(e);
                    } while (e.u != u || e.v != v);
                    biconnectedComponents.add(bcc);
                }
            }
            else if (v != parent[u] && disc[v] < disc[u]) {          //If visited node found, but it is not parent node and is an ancestor node then update the LLV value
                                                                      //of the currentNode = Node Id of the ancestor, also push the edge to stack
                // Back edge
                low[u] = Math.min(low[u], disc[v]);
                edgeStack.push(new Edge(u, v));
            }
        }
    }

    public List<List<Edge>> getBiconnectedComponents() {
        // Pop remaining edges for possible leftover BCC after DFS
        while (!edgeStack.isEmpty()) {
            List<Edge> bcc = new ArrayList<>();
            while (!edgeStack.isEmpty())
                bcc.add(edgeStack.pop());
            biconnectedComponents.add(bcc);
        }
        return biconnectedComponents;
    }

    public List<Integer> getArticulationPoints() {
        List<Integer> points = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (articulationPoint[i])
                points.add(i);
        return points;
    }

    // Example usage
    public static void main(String[] args) {
        int n = 5;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        // Add edges (undirected)
        graph.get(0).add(1); graph.get(1).add(0);
        graph.get(1).add(2); graph.get(2).add(1);
        graph.get(1).add(3); graph.get(3).add(1);
        graph.get(3).add(4); graph.get(4).add(3);

        TarjanBCC tarjan = new TarjanBCC(graph);
        List<List<Edge>> bccs = tarjan.getBiconnectedComponents();
        List<Integer> aps = tarjan.getArticulationPoints();

        System.out.println("Biconnected Components (as edge lists):");
        for (List<Edge> bcc : bccs)
            System.out.println(bcc);
        System.out.println("Articulation Points:");
        System.out.println(aps);
    }
}

/**
 * https://link...
 * Problem Number/Code - Problem Name
 * Description:
 * Catergory:
 * Solution:
 * Issues:
 * Author: Vu Thanh Trung
 */

import java.io.*;
import java.util.*;

// This class represents a directed graph using adjacency list
// representation
class Graph {
    int V;   // No. of vertices
//    private LinkedList<Integer> adj[]; //Adjacency List
    HashSet<Integer> adj[]; //Adjacency List
    int[] weight;

    //Constructor
    Graph(int v) {
        V = v;
        weight = new int[v];
//        adj = new LinkedList[v];
        adj = new HashSet[v];
        for (int i = 0; i < v; ++i) {
//            adj[i] = new LinkedList();
            adj[i] = new HashSet<>();
        }
    }

    //Function to add an edge into the graph
    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // A recursive function to print DFS starting from v
    void DFSUtil(int v, boolean visited[], LinkedList<Integer> scc, HashSet<Integer> set) {
        // Mark the current node as visited and print it
        visited[v] = true;
//        System.out.print(v + " ");
        scc.addLast(v);
        set.add(v);
        int n;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            n = i.next();
            if (!visited[n])
                DFSUtil(n, visited, scc, set);
        }
    }

    // Function that returns reverse (or transpose) of this graph
    Graph getTranspose() {
        Graph g = new Graph(V);
        for (int v = 0; v < V; v++) {
            // Recur for all the vertices adjacent to this vertex
//            Iterator<Integer> i = adj[v].listIterator();
            Iterator<Integer> i = adj[v].iterator();

            while (i.hasNext()) {
                g.adj[i.next()].add(v);
            }
        }
        return g;
    }

    void fillOrder(int v, boolean visited[], Stack stack) {
        // Mark the current node as visited and print it
        visited[v] = true;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                fillOrder(n, visited, stack);
        }

        // All vertices reachable from v are processed by now,
        // push v to Stack
        stack.push(new Integer(v));
    }

    // The main function that finds and prints all strongly
    // connected components
    void printSCCs() {
        Stack stack = new Stack();

        // Mark all the vertices as not visited (For first DFS)
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++) {
            visited[i] = false;
            weight[i] = 1;
        }

        // Fill vertices in stack according to their finishing
        // times
        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                fillOrder(i, visited, stack);

        // Create a reversed graph
        Graph gr = getTranspose();

        // Mark all the vertices as not visited (For second DFS)
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Now process all vertices in order defined by Stack
        while (stack.empty() == false) {
            // Pop a vertex from stack
            int v = (int) stack.pop();
            LinkedList<Integer> scc = new LinkedList<>();
            HashSet<Integer> set = new HashSet<>();
            // Print Strongly connected component of the popped vertex
            if (visited[v] == false) {
                gr.DFSUtil(v, visited, scc, set);
                collapse(scc, gr, set);
//                System.out.println();
            }
        }
    }

    private void collapse(LinkedList<Integer> scc, Graph gr, HashSet<Integer> set) {
        // Choose one node to be the single node of the whole scc.
        int theOne = scc.pollFirst();
        // Remove th
        for (int v : set) {
            adj[theOne].remove(v);
        }
        while (!scc.isEmpty()) {
            int v = scc.pollFirst();
            weight[theOne] += weight[v];
            // First delete all edges from v to v's children and draw
            // new edges from theOne to those children, excluding those in the
            // scc.
            for (int w : adj[v]) {
                if (!set.contains(w)) {
                    adj[theOne].add(w);
                }
            }
            // Then delete all edges from predecessors of v and draw new edges
            // from those predecessors to theOne.
            for (int w : gr.adj[v]) {
                if (!set.contains(w)) {
                    adj[w].add(theOne);
                }
            }
            adj[v] = new HashSet<>();
        }
    }

    int longestPath() {
        boolean[] visited = new boolean[V];
        int max = 1;
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, visited);
            }
        }
        for (int i = 0; i < V; i++) {
            max = Math.max(max, weight[i]);
        }
        return max;
    }

    private void dfs(int i, boolean[] visited) {
        visited[i] = true;
//        dp[i] = 1;
//        isFree[i] = 0;
        int max = weight[i];
        for (int j : adj[i]) {
            if (!visited[j]) {
                dfs(j, visited);
            }
//            if (isFree[j] != 0) {
            max = Math.max(max, weight[j] + weight[i]);
//            }
        }
        weight[i] = max;
//        isFree[i] = 1;
//        globalMax = Math.max(globalMax, dp[i]);
//        System.out.println("dp[" + i + "] = " + dp[i]);
    }

//    private int dfs(int i, boolean[] visited) {
//        int max = weight[i];
//        visited[i] = true;
//        for (int j : adj[i]) {
//            if (visited[j]) {
//                max = Math.max(max, weight[i] + dfs(j, visited));
//            }
//        }
//        weight[i] = max;
//        return max;
//    }
}


public class Main {

    static BufferedReader br;
//    static PrintWriter pw;
    static StringTokenizer st;
//    static HashSet<Integer>[] adjLists;
    static int n, m;
    static Graph g;
    //
    public static void main(String[] args) throws Exception {

//        System.setIn(new FileInputStream("input.txt"));
//        System.setOut(new PrintStream("output.txt"));

        br = new BufferedReader(new InputStreamReader(System.in));
//        pw = new PrintWriter(new BufferedOutputStream(System.out));

        input();

        // Create a graph given in the above diagram

//        System.out.println("Following are strongly connected components "+
//                "in given graph ");
        g.printSCCs();

//        for (int i = 0; i < n; ++i) {
//            System.out.println(i + ": " + g.adj[i] + " " + g.weight[i]);
//        }
        System.out.println(g.longestPath());
//        for (int i = 0; i < n; ++i) {
//            System.out.println(i + ": " + g.adj[i] + " " + g.weight[i]);
//        }
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
//        adjLists = new HashSet[n];
        g = new Graph(n);
//        for (int i = 0; i < n; i++) {
//            adjLists[i] = new HashSet();
//        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
//            adjLists[u].add(v);
            g.addEdge(u, v);
        }
    }
}

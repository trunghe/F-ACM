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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static PrintWriter pw;
    static StringTokenizer st;
    static HashSet<Integer>[] adjLists;
    static int n, m;
    static Integer[] dp;
//    static int globalMax;
    static LinkedList<Integer> parent;
    static int[] visited;
    static boolean mightStillHaveStronglyConnectedComponents;
//
    public static void main(String[] args) throws Exception {

        System.setIn(new FileInputStream("input.txt"));
        System.setOut(new PrintStream("output.txt"));

        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(new BufferedOutputStream(System.out));

        input();

//        for (int i = 0; i < n; i++) {
//            System.out.println(i + ": " + adjLists[i]);
//        }
        
        collapseStronglyConnectedComponents();


//        for (int i = 0; i < n; i++) {
//            System.out.println(i + ": " + adjLists[i]);
//        }
    }

    private static void collapseStronglyConnectedComponents() {
        dp = new Integer[n];
        for (int u = 0; u < n; u++) {
            dp[u] = 1;
        }
        mightStillHaveStronglyConnectedComponents = true;
        while (mightStillHaveStronglyConnectedComponents) {
            //
            for (int i = 0; i < n; i++) {
                System.out.println(i + ": " + adjLists[i]);
            }
            System.out.println();
            // Re-initialize.
            mightStillHaveStronglyConnectedComponents = false;
            parent = new LinkedList<>();
            visited = new int[n];
            for (int u = 0; u < n; u++) {
                visited[u] = -1;
            }
            //
            for (int u = 0; u < n; u++) {
                if (visited[u] == -1) {
                    dfs1(u);
                }
            }
        }
    }

    private static void dfs1(int u) {

        System.out.println("u = " + u);

        parent.addLast(u);
        visited[u] = 0;
        for (Integer v : adjLists[u].toArray(new Integer[0])) {
            if (visited[v] == 0) { // Found a strongly connected component.

                System.out.println("v = " + v);
                System.out.println("adjLists[v] = " + adjLists[v]);

                mightStillHaveStronglyConnectedComponents = true;
                // Collapse the SCC to a single node with dp == total number of
                // nodes in the SCC.
                int k;
                LinkedList<Integer> scc = new LinkedList<>();
                HashSet<Integer> set = new HashSet<>();
                // Separate the SCC.
                int count = 0;

                do {
                    scc.addLast(k = parent.pollLast());
                    set.add(k);
                    count += dp[k];
                } while (k != v);
                // Now remove the edges and connect them to one single node.
                int theOne = scc.pollLast();
                dp[theOne] = count;
                for (int p : set) {
                    adjLists[theOne].remove(p);
                }
                int p;
                while (!scc.isEmpty()) {
                    p = scc.pollFirst();
                    dp[p] = count;
                    for (int q : adjLists[p]) {
                        if (!set.contains(q)) {
                            adjLists[theOne].add(q);
                        }
                    }
                    adjLists[p] = new HashSet();
                    for (int q = 0; q < n; ++q) {
                        for (int w : set) {
                            if (adjLists[q].remove(w)) {
                                adjLists[q].add(theOne);
                            }
                        }
                    }
                }
            } else if (visited[v] == -1) {
                dfs1(v);
            }
        }
        visited[u] = 1;
    }


    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        adjLists = new HashSet[n];
        for (int i = 0; i < n; i++) {
            adjLists[i] = new HashSet();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            adjLists[u].add(v);
        }
    }
}

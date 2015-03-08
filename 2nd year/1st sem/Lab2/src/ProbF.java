import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Created by lightning95 on 10/5/14.
 */

public class ProbF {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbF().run();
    }

    public String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return "-1";
            }
        }
        return st.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "biconv";
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + ".out");
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    int[] tup;
    int[] tin;
    ArrayList<Edge>[] edges;
    int time;
    Stack<Edge> stack;
    int[] color;
    int id = -1;

    // u -> v
    class Edge {
        int u;
        int v;
        int id;

        Edge(int u, int v, int id) {
            this.u = u;
            this.v = v;
            this.id = id;
        }
    }

    // p == -1 <=> u == root
    void dfs(int u, int p) {
        tin[u] = ++time;
        tup[u] = time;

        int children = 0;

        for (Edge e : edges[u]) {
            if (tin[e.v] == 0) {
                int size = stack.size();
                stack.add(e);
                ++children;
                dfs(e.v, u);
                tup[u] = Math.min(tup[u], tup[e.v]);
                if (p != -1 && tup[e.v] >= tin[u]) {
                    // u - cut vertex
                    ++id;
                    System.err.println("HERE : " + u + " " + e.v);
                    while (stack.size() > size){
                        color[stack.pop().id] = id;
                    }
                }
            } else if (p != e.v) {
                tup[u] = Math.min(tup[u], tin[e.v]);
                if (tin[u] > tin[e.v]){
                    stack.add(e);
                }
            }
        }

        if (p == -1 && children > 1) {
            // u - cut vertex && root

        }
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();
        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            edges[u].add(new Edge(u, v, i));
            edges[v].add(new Edge(v, u, i));
        }

        tup = new int[n];
        tin = new int[n];
        color = new int[m];
        stack = new Stack<>();
        for (int i = 0; i < n; ++i) {
            if (tin[i] == 0) {
                dfs(i, -1);
                ++id;
                while (!stack.isEmpty()){
                    Edge e = stack.pop();
                    color[e.id] = id;
                    if (e.u == i){
                        ++id;
                    }
                }
            }
        }

        out.println(id);

        for (int i : color) {
            out.print((i + 1) + " ");
        }
        out.println();
    }
}
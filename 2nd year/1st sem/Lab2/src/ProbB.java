import java.io.*;
import java.util.*;

/**
 * Created by lightning95 on 10/5/14.
 */

public class ProbB {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbB().run();
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
        String name = "bridges";
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
    ArrayList<Integer> bridges;

    // u -> v
    class Edge {
        int v, id;

        Edge(int v, int id) {
            this.v = v;
            this.id = id;
        }
    }

    void dfs(int u, int p) {
        tin[u] = ++time;
        tup[u] = time;

        for (Edge e : edges[u]) {
            if (tin[e.v] == 0) {
                dfs(e.v, u);
                tup[u] = Math.min(tup[u], tup[e.v]);
                if (tup[e.v] > tin[u]) {
                    bridges.add(e.id);
                }
            } else if (p != e.v) {
                tup[u] = Math.min(tup[u], tin[e.v]);
            }
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
            edges[u].add(new Edge(v, i));
            edges[v].add(new Edge(u, i));
        }

        tup = new int[n];
        tin = new int[n];
        bridges = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (tin[i] == 0) {
                dfs(i, -1);
            }
        }

        out.println(bridges.size());

        Collections.sort(bridges);
        for (int i : bridges) {
            out.print((i + 1) + " ");
        }
        out.println();
    }
}
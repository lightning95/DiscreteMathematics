import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;


public class ProbE {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbE().run();
    }

    String next() {
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

    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    int nextInt() {
        return Integer.parseInt(next());
    }

    void run() throws IOException {
        String name = "chinese";
        InputStream input = System.in;
        OutputStream output = System.out;
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new FileOutputStream(name + ".out");
            }
        } catch (Exception e) {
        }

        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);

        solve();

        br.close();
        out.close();
    }

    int time;

    void revDfs(int u, ArrayList<Edge>[] edges, boolean[] was, int[] tout) {
        was[u] = true;

        for (Edge e : edges[u]) {
            if (!was[e.v]) {
                revDfs(e.v, edges, was, tout);
            }
        }

        tout[time++] = u;
    }

    void dfs(int u, ArrayList<Edge>[] edges, int[] color, int col) {
        color[u] = col;

        for (Edge e : edges[u]) {
            if (color[e.v] == -1) {
                dfs(e.v, edges, color, col);
            }
        }
    }

    int[] condensation(int n, ArrayList<Edge>[] edges) {
        boolean[] was = new boolean[n];
        int[] tout = new int[n];
        ArrayList<Edge>[] revEdges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            revEdges[i] = new ArrayList<Edge>();
        }
        for (ArrayList<Edge> list : edges) {
            for (Edge e : list) {
                revEdges[e.v].add(new Edge(e.v, e.u, 0));
            }
        }
        time = 0;
        for (int i = 0; i < n; ++i) {
            if (!was[i]) {
                revDfs(i, revEdges, was, tout);
            }
        }
        int[] color = new int[n];
        Arrays.fill(color, -1);
        for (int i = n - 1, col = 0; i >= 0; --i) {
            if (color[tout[i]] == -1) {
                dfs(tout[i], edges, color, col++);
            }
        }

        return color;
    }

    boolean bad;

    int dfs(int u, ArrayList<Edge>[] edges, boolean[] us) {
        int res = 1;
        us[u] = true;
        for (Edge e : edges[u]) {
            if (!us[e.v]) {
                res += dfs(e.v, edges, us);
            }
        }
        return res;
    }

    long findMST(Edge[] edges, int n, int root) {
        long res = 0;
        long[] minEdge = new long[n];
        Arrays.fill(minEdge, Long.MAX_VALUE / 2);
        for (Edge e : edges) {
            minEdge[e.v] = Math.min(e.w, minEdge[e.v]);
        }
        minEdge[root] = 0;
        for (int v = 0; v < n; ++v) {
            if (v != root) {
                if (minEdge[v] == Long.MAX_VALUE / 2) {
                    bad = true;
                    return 0;
                }
                res += minEdge[v];
            }
        }

        ArrayList<Edge>[] zeroEdges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            zeroEdges[i] = new ArrayList<Edge>();
        }
        for (Edge e : edges) {
            if (e.w == minEdge[e.v]) {
                zeroEdges[e.u].add(new Edge(e.u, e.v, 0));
            }
        }
//
        if (dfs(root, zeroEdges, new boolean[n]) == n) {
            return res;
        }
//
        int[] comp = condensation(n, zeroEdges);
        int compCount = 0;
        for (int i : comp) {
            compCount = Math.max(compCount, i);
        }
        ArrayList<Edge> newEdges = new ArrayList<Edge>();
        for (Edge e : edges) {
            if (comp[e.u] != comp[e.v]) {
                newEdges.add(new Edge(comp[e.u], comp[e.v], e.w - minEdge[e.v]));
            }
        }
//
        res += findMST(newEdges.toArray(new Edge[1]), compCount + 1, comp[root]);
        if (bad) {
            return 0;
        }
        return res;
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();

        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            int w = nextInt();
            edges[i] = new Edge(u, v, w);
        }
        long ans = findMST(edges, n, 0);
        out.println(!bad ? "YES\n" + ans : "NO");
    }

    class Edge {
        int u;
        int v;
        long w;

        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }
}

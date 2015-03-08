import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbC {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbC().run();
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
        String name = "multiassignment";
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

    ArrayList<Edge> findPath(int n, int source, int sink) {
        int[] d = new int[n];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[source] = 0;
        Edge[] prev = new Edge[n];
        Arrays.fill(prev, null);

        for (int i = 1; i < n; ++i) {
            boolean change = false;
            for (Edge e : allEdges) {
                if (d[e.u] < Integer.MAX_VALUE && e.flow < e.cap && d[e.u] + e.cost < d[e.v]) {
                    d[e.v] = d[e.u] + e.cost;
                    prev[e.v] = e;
                    change = true;
                }
            }
            if (!change) {
                break;
            }
        }

        if (d[sink] < Integer.MAX_VALUE) {
            ArrayList<Edge> res = new ArrayList<>();
            for (Edge e = prev[sink]; e != null; e = prev[e.u]) {
                res.add(e);
            }
            return res;
        } else {
            return null;
        }
    }

    Edge[] allEdges;
    boolean[] was;
    int[] pair;
    Edge[][] edges;

    boolean dfs(int u) {
        was[u] = true;
        for (Edge e : edges[u]) {
            if (e.flow > 0 && (pair[e.v] == -1 || !was[pair[e.v]] && dfs(pair[e.v]))) {
                pair[e.v] = u;
                return true;
            }
        }

        return false;
    }

    void solve() {
        int n = nextInt();
        int k = nextInt();

        allEdges = new Edge[(n * n + n * 2) * 2];
        edges = new Edge[n][n];

        for (int u = 0, i = 0; u < n; ++u) {
            for (int v = 0; v < n; ++v) {
                int cost = nextInt();

                Edge e = new Edge(u, v + n, cost, 1);
                Edge rev = new Edge(v + n, u, -cost, 0);
                e.rev = rev;
                rev.rev = e;

                edges[u][v] = e;
                allEdges[i++] = e;
                allEdges[i++] = rev;
            }
        }

        for (int i = 0, id = n * n * 2; i < n; ++i) {
            {
                Edge e = new Edge(n * 2, i, 0, k);
                Edge rev = new Edge(i, 2 * n, 0, 0);
                e.rev = rev;
                rev.rev = e;

                allEdges[id++] = e;
                allEdges[id++] = rev;
            }
            {
                Edge e = new Edge(i + n, 2 * n + 1, 0, k);
                Edge rev = new Edge(n * 2 + 1, i + n, 0, 0);
                e.rev = rev;
                rev.rev = e;

                allEdges[id++] = e;
                allEdges[id++] = rev;
            }
        }


        long ans = 0;
        for (; ; ) {
            ArrayList<Edge> path = findPath(2 * n + 2, n * 2, 2 * n + 1);
            if (path == null) {
                break;
            }
            long min = Integer.MAX_VALUE;
            for (Edge e : path) {
                min = Math.min(min, e.cap - e.flow);
            }
            for (Edge e : path) {
                ans += min * e.cost;
                e.flow += min;
                e.rev.flow -= min;
            }
        }
        out.println(ans);

        pair = new int[n * 2];
        was = new boolean[n * 2];

        int[] rev = new int[n * 2];
        for (int i = 0; i < k; ++i) {
            Arrays.fill(pair, -1);
            for (int j = 0; j < n; ++j) {
                Arrays.fill(was, false);
                dfs(j);
            }

            for (int j = n; j < n * 2; ++j) {
                int u = pair[j];
                int v = j - n;
                rev[u] = v;
                --edges[u][v].flow;
            }

            for (int j = 0; j < n; ++j) {
                out.print(rev[j] + 1 + " ");
            }
            out.println();
        }
    }

    class Edge {
        int u;
        int v;
        int cost;
        int cap;
        int flow;
        Edge rev;

        Edge(int u, int v, int cost, int cap) {
            this.u = u;
            this.v = v;
            this.cost = cost;
            this.cap = cap;
            flow = 0;
        }
    }
}

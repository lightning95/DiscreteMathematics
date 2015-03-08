import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbB {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbB().run();
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
        String name = "mincost";
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
            ArrayList<Edge> res = new ArrayList<Edge>();
            for (Edge e = prev[sink]; e != null; e = prev[e.u]) {
                res.add(e);
            }
            return res;
        } else {
            return null;
        }
    }

    Edge[] allEdges;

    void solve() {
        int n = nextInt();
        int m = nextInt();

        allEdges = new Edge[m << 1];

        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            int cap = nextInt();
            int cost = nextInt();

            Edge e = new Edge(u, v, cost, cap);
            Edge rev = new Edge(v, u, -cost, 0);
            e.rev = rev;
            rev.rev = e;

            allEdges[i] = e;
            allEdges[i + m] = rev;
        }

        long ans = 0;
        for (; ; ) {
            ArrayList<Edge> path = findPath(n, 0, n - 1);
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

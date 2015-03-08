import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class ProbF {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbF().run();
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
        String name = "circulation";
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

    boolean bfs(int source, int sink, int cap) {
        q.clear();
        q.add(source);

        Arrays.fill(d, Integer.MAX_VALUE);
        d[source] = 0;
        while (!q.isEmpty() && d[sink] == Integer.MAX_VALUE) {
            int u = q.remove();
            for (Edge e : edges[u]) {
                if (e.f + cap <= e.c && d[u] + 1 < d[e.v]) {
                    d[e.v] = d[u] + 1;
                    q.add(e.v);
                }
            }
        }

        return d[sink] < Integer.MAX_VALUE;
    }

    Queue<Integer> q;
    int[] d;
    ArrayList<Edge>[] edges;
    int[] start;

    int dfs(int u, int sink, int flow) {
        if (u == sink) {
            return flow;
        }

        for (; start[u] < edges[u].size(); ) {
            Edge e = edges[u].get(start[u]++);
            if (d[u] + 1 == d[e.v] && e.f < e.c) {
                int res = dfs(e.v, sink, Math.min(flow, e.c - e.f));
                if (res > 0) {
                    e.f += res;
                    e.rev.f -= res;
                    return res;
                }
            }
        }

        return 0;
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();

        edges = new ArrayList[n + 2];
        for (int i = 0; i < edges.length; ++i) {
            edges[i] = new ArrayList<>();
        }

        Edge[] allEdges = new Edge[m];

        for (int i = 0; i < m; ++i) {
            int u = nextInt();
            int v = nextInt();
            int cmin = nextInt();
            int cmax = nextInt();
            {
                Edge e = new Edge(0, v, cmin);
                Edge rev = new Edge(v, 0, 0);
                e.rev = rev;
                rev.rev = e;
                edges[0].add(e);
                edges[v].add(rev);
            }
            {
                Edge e = new Edge(u, v, cmax - cmin);
                Edge rev = new Edge(v, u, 0);
                e.rev = rev;
                rev.rev = e;
                edges[u].add(e);
                edges[v].add(rev);

                allEdges[i] = e;
                e.oldCap = cmin;
            }
            {
                Edge e = new Edge(u, n + 1, cmin);
                Edge rev = new Edge(n + 1, u, 0);
                e.rev = rev;
                rev.rev = e;
                edges[u].add(e);
                edges[n + 1].add(rev);
            }
        }

        d = new int[n + 2];
        q = new ArrayDeque<>();
        start = new int[n + 2];
        for (int cap = 1 << 29; cap > 0; cap >>= 1) {
            while (bfs(0, n + 1, cap)) {
                Arrays.fill(start, 0);
                while (dfs(0, n + 1, Integer.MAX_VALUE) > 0) {
                }
            }
        }

        boolean ok = edges[0].size() == Arrays.stream(edges[0].toArray(new Edge[1])).filter(e -> e.f == e.c).count();
        out.println(ok ? "YES" : "NO");
        if (ok){
            Arrays.stream(allEdges).forEach(e -> out.println(e.f + e.oldCap));
        }
    }

    class Edge {
        int u;
        int v;
        int c;
        int f;
        Edge rev;
        int oldCap;

        Edge(int u, int v, int c) {
            this.u = u;
            this.v = v;
            this.c = c;
            f = 0;
        }
    }
}

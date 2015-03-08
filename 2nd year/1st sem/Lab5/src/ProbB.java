import java.io.*;
import java.util.*;

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
        String name = "maxflow";
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

    ArrayList<Edge> bfs() {
        Queue<Integer> q = new ArrayDeque<Integer>();
        int n = edges.length;
        int[] d = new int[n];
        Edge[] prev = new Edge[n];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[0] = 0;
        q.add(0);
        while (!q.isEmpty()) {
            int u = q.remove();
            for (Edge e : edges[u]) {
                if (e.f < e.c && d[u] + 1 < d[e.v]) {
                    d[e.v] = d[u] + 1;
                    prev[e.v] = e;
                    q.add(e.v);
                }
            }
        }

        if (d[n - 1] < Integer.MAX_VALUE) {
            ArrayList<Edge> ans = new ArrayList<Edge>();
            for (int cur = n - 1; cur > -1 && prev[cur] != null; cur = prev[cur].u) {
                ans.add(prev[cur]);
            }
            Collections.reverse(ans);
            return ans;
        }
        return null;
    }

    ArrayList<Edge>[] edges;

    void solve() {
        int n = nextInt();
        int m = nextInt();

        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<Edge>();
        }

        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            int c = nextInt();

            Edge e = new Edge(u, v, c);
            Edge rev = new Edge(v, u, 0);
            e.rev = rev;
            rev.rev = e;
            edges[u].add(e);
            edges[v].add(rev);
        }

        int ans = 0;
        for (ArrayList<Edge> path = bfs(); path != null && !path.isEmpty(); path = bfs()) {
            int min = Integer.MAX_VALUE;
            for (Edge e : path) {
                min = Math.min(min, e.c - e.f);
            }
            for (Edge e : path) {
                e.f += min;
                e.rev.f -= min;
            }
            ans += min;
        }
        out.println(ans);
    }

    class Edge {
        int u;
        int v;
        int c;
        int f;
        Edge rev;

        Edge(int u, int v, int c) {
            this.u = u;
            this.v = v;
            this.c = c;
            f = 0;
        }
    }
}

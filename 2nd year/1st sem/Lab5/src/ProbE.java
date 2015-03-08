import java.io.*;
import java.util.*;

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
        String name = "decomposition";
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

    boolean bfs(int cap) {
        q.clear();
        q.add(0);

        int n = edges.length;
        Arrays.fill(d, Integer.MAX_VALUE);
        d[0] = 0;
        while (!q.isEmpty() && d[n - 1] == Integer.MAX_VALUE) {
            int u = q.remove();
            for (Edge e : edges[u]) {
                if (e.f + cap <= e.c && d[u] + 1 < d[e.v]) {
                    d[e.v] = d[u] + 1;
                    q.add(e.v);
                }
            }
        }

        return d[n - 1] < Integer.MAX_VALUE;
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

    boolean[] vis;

    int findPath(int u, int sink, int flow, ArrayList<Integer> list) {
        if (u == sink) {
            return flow;
        }
        vis[u] = true;
        for (Edge e : edges[u]) {
            if (e.f > 0 && !vis[e.v]) {
                int res = findPath(e.v, sink, Math.min(flow, e.f), list);
                e.f -= res;
                e.rev.f += res;
                list.add(e.id);
                return res;
            }
        }
        return 0;
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
            int c = nextInt();

            Edge e = new Edge(u, v, c, i);
            Edge rev = new Edge(v, u, 0, i);
            e.rev = rev;
            rev.rev = e;
            edges[u].add(e);
            edges[v].add(rev);
        }

        d = new int[n];
        q = new ArrayDeque<>();

        start = new int[n];
        for (int cap = 1 << 29; cap > 0; cap >>= 1) {
            while (bfs(cap)) {
                Arrays.fill(start, 0);
                while (dfs(0, n - 1, Integer.MAX_VALUE) > 0) {
                }
            }
        }

        ArrayList<Path> ans = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        vis = new boolean[n];

        for (; ; ) {
            list.clear();
            Arrays.fill(vis, false);
            int flow = findPath(0, n - 1, Integer.MAX_VALUE, list);
            if (flow == 0) {
                break;
            }
            Collections.reverse(list);
            ans.add(new Path(flow, list.toArray(new Integer[1])));
        }

        out.println(ans.size());
        for (Path p : ans) {
            out.print(p.f + " " + p.a.length + " ");
            Arrays.stream(p.a).forEach(i -> out.print((i + 1) + " "));
            out.println();
        }
    }

    class Path {
        Integer[] a;
        int f;

        Path(int f, Integer[] a) {
            this.f = f;
            this.a = new Integer[a.length];
            System.arraycopy(a, 0, this.a, 0, a.length);
        }
    }

    class Edge {
        int u;
        int v;
        int c;
        int f;
        int id;
        Edge rev;

        Edge(int u, int v, int c, int id) {
            this.u = u;
            this.v = v;
            this.c = c;
            this.id = id;
            f = 0;
        }
    }
}

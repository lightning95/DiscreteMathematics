import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 10/17/14.
 */

public class ProbE {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbE().run();
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
        String name = "path";
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

    // u -> v
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

    ArrayList<Edge>[] edges;
    boolean[] was;

    void dfs(int u) {
        was[u] = true;
        for (Edge e : edges[u]) {
            if (!was[e.v]) {
                dfs(e.v);
            }
        }
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();
        int start = nextInt() - 1;
        ArrayList<Edge> allEdges = new ArrayList<>();
        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            long w = nextLong();
            allEdges.add(new Edge(u, v, w));
            edges[u].add(new Edge(u, v, w));
        }

        long[] d = new long[n];
        int[] prev = new int[n];

        final long INF = 4_000_000_000_000_000_000L;

        Arrays.fill(d, INF);
        d[start] = 0;
        prev[start] = start;

        boolean[] onCycle = new boolean[n];
        ArrayList<Integer> cycles = new ArrayList<>();

        for (int k = 0; k < n; ++k) {
            for (Edge e : allEdges) {
                if (d[e.u] != INF && d[e.u] + e.w < d[e.v]) {
                    d[e.v] = Math.max(d[e.u] + e.w, -INF); // distance is less than -10^18
                    prev[e.v] = e.u;
                    if (k + 1 == n) {
                        // MAYBE TODO
                        int last = e.u;
                        if (!onCycle[last]) {
                            for (int i = 0; i < n; ++i) {
                                last = prev[last];
                            }
                            for (int i = 0; i < n; ++i) {
                                if (!onCycle[last]) {
                                    cycles.add(last);
                                    onCycle[last] = true;
                                }
                                last = prev[last];
                            }
                        }
                    }
                }
            }
        }
        was = new boolean[n];
        if (!cycles.isEmpty()) {
            dfs(start);
            ArrayList<Integer> reachable = new ArrayList<>();
            for (int u : cycles) {
                if (was[u]) {
                    reachable.add(u);
                }
            }
            Arrays.fill(was, false);
            for (int u : reachable) {
                if (!was[u]) {
                    dfs(u);
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            if (d[i] == INF) {
                out.println("*");
            } else if (was[i]) {
                out.println("-");
            } else {
                out.println(d[i]);
            }
        }
    }

    public long nextLong() {
        return Long.parseLong(nextToken());
    }
}
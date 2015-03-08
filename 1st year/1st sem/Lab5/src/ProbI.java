import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbI {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbI().run();
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

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    String nextLine() throws IOException {
        return br.readLine();
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "salesman.";
        try {
            File f = new File(name + "in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + "out");
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    void solve() {
        int n = nextInt(), m = nextInt();
        ArrayList<Edge>[] edges = new ArrayList[n];
        int[][] dp = new int[1 << n][n];
        for (int mask = 0; mask < 1 << n; ++mask) {
            Arrays.fill(dp[mask], Integer.MAX_VALUE);
        }
        for (int i = 0; i < n; ++i) {
            dp[1 << i][i] = 0;
            edges[i] = new ArrayList<Edge>();
        }
        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1, v = nextInt() - 1, w = nextInt();
            edges[u].add(new Edge(v, w));
            edges[v].add(new Edge(u, w));
        }
        for (int mask = 1; mask < 1 << n; ++mask) {
            for (int u = 0; u < n; ++u) {
                if (dp[mask][u] < Integer.MAX_VALUE) {
                    for (Edge e : edges[u]) {
                        int v = e.v, w = e.w;
                        if ((mask & (1 << v)) == 0) {
                            dp[mask | (1 << v)][v] = Math.min(dp[mask | (1 << v)][v], dp[mask][u] + w);
                        }
                    }
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            ans = Math.min(ans, dp[(1 << n) - 1][i]);
        }
        out.println(ans < Integer.MAX_VALUE ? ans : -1);
    }

    class Edge {
        int v, w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProbH {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;
    ArrayList<Edge>[] edges;
    long[][] dp;

    public static void main(String[] args) throws IOException {
        new ProbH().run();
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
        String name = "matching.";
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

    void dp(int u, int pr) {
        dp[u][0] = 0;
        for (Edge e : edges[u]) {
            if (e.v != pr) {
                dp(e.v, u);
                dp[u][0] += dp[e.v][1];
            }
        }
        dp[u][1] = dp[u][0];
        for (Edge e : edges[u]) {
            if (e.v != pr) {
                dp[u][1] = Math.max(dp[u][1], dp[u][0] - dp[e.v][1] + dp[e.v][0] + e.w);
            }
        }
    }

    void solve() {
        int n = nextInt();
        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<Edge>();
        }
        for (int i = 1; i < n; ++i) {
            int u = nextInt() - 1, v = nextInt() - 1, w = nextInt();
            edges[u].add(new Edge(v, w));
            edges[v].add(new Edge(u, w));
        }
        dp = new long[n][2];
        dp(0, -1);
        out.println(dp[0][1]);
    }

    class Edge {
        int v, w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
}
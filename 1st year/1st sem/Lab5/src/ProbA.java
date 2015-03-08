import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbA {
    final int md = 1000000007;
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;
    int[] d;
    ArrayList<Integer>[] edges;

    public static void main(String[] args) throws IOException {
        new ProbA().run();
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
        String name = "countpaths.";
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

    int rec(int u) {
        if (d[u] == -1) {
            d[u] = 0;
            for (int v : edges[u]) {
                if (d[v] == -1) {
                    rec(v);
                }
                d[u] += d[v];
                d[u] %= md;
            }
        }
        return d[u];
    }

    void solve() {
        int n = nextInt(), m = nextInt();
        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1, v = nextInt() - 1;
            edges[v].add(u);
        }
        d = new int[n];
        Arrays.fill(d, -1);
        d[0] = 1;
        out.println(rec(n - 1));
    }
}
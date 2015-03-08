import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbD {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbD().run();
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
        String name = "minimax";
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

    boolean[] was;
    int[] pair;
    int[][] a;

    boolean dfs(int u, int c) {
        was[u] = true;
        int n = was.length;
        for (int v = 0; v < n; ++v) {
            if (a[u][v] >= c && (pair[v] < 0 || !was[pair[v]] && dfs(pair[v], c))){
                pair[v] = u;
                return true;
            }
        }

        return false;
    }

    void solve() {
        int n = nextInt();
        a = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = nextInt();
            }
        }

        was = new boolean[n];
        pair = new int[n];
        int ans = 0;
        for (int l = 0, r = 1_000_000; l < r; ) {
            int m = (l + r) >> 1;
            int c = 0;
            Arrays.fill(pair, -1);
            for (int i = 0; i < n; ++i) {
                Arrays.fill(was, false);
                if (dfs(i, m)) {
                    ++c;
                }
            }
            if (c == n) {
                l = m + 1;
                ans = Math.max(ans, m);
            } else {
                r = m;
            }
        }
        out.println(ans);
    }
}

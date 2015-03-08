import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbJ {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;
    int[][] mid;
    StringBuilder sb;

    public static void main(String[] args) throws IOException {
        new ProbJ().run();
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
        String name = "optimalcode.";
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

    void rec(int l, int r) {
        if (l > r) {
            return;
        }
        if (l == r) {
            out.println(sb.toString());
            return;
        }
        int m = mid[l][r];
        sb.append(0);
        rec(l, m);
        sb.replace(sb.length() - 1, sb.length(), "1");
        rec(m + 1, r);
        sb.deleteCharAt(sb.length() - 1);
    }

    void solve() {
        int n = nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = nextInt();
        }
        if (n == 1) {
            out.println(a[0]);
            out.println(0);
            return;
        }
        long[] s = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            s[i + 1] = s[i] + a[i];
        }
        long[][] dp = new long[n][n];
        mid = new int[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(dp[i], Long.MAX_VALUE / 2);
            dp[i][i] = 0;
            mid[i][i] = i;
            if (i + 1 < n) {
                mid[i][i + 1] = i;
            }
        }

        for (int len = 1; len < n; ++len) {
            for (int l = 0; l + len < n; ++l) {
                int r = l + len;
                int mLeft = mid[l][r - 1];
                int mRight = mid[l + 1][r];
                for (int m = mLeft; m <= mRight && m + 1 < n; ++m) {
                    long cur = s[r + 1] - s[l] + dp[l][m] + dp[m + 1][r];
                    if (cur < dp[l][r]) {
                        dp[l][r] = cur;
                        mid[l][r] = m;
                    }
                }
            }
        }
        out.println(dp[0][n - 1]);
        sb = new StringBuilder();
        rec(0, n - 1);
    }
}
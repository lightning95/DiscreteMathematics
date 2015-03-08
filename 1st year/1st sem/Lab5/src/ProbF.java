import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbF {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;
    Pair[] a;
    String[][] s;
    long[][] dp;

    public static void main(String[] args) throws IOException {
        new ProbF().run();
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
        String name = "matrix.";
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
        if (!s[l][r].equals("")) {
            return;
        }
        if (l + 1 == r) {
            dp[l][r] = a[l].x * a[l].y * a[r].y;
            s[l][r] = "(AA)";
            return;
        }
        if (l == r) {
            dp[l][r] = 0;
            s[l][r] = "A";
            return;
        }
        int id = l;
        for (int i = l; i < r; ++i) {
            rec(l, i);
            rec(i + 1, r);
            long cL = dp[l][i];
            long cR = dp[i + 1][r];
            if (cL + cR + a[l].x * a[i].y * a[r].y < dp[l][r]) {
                dp[l][r] = cL + cR + a[l].x * a[i].y * a[r].y;
                id = i;
            }
        }
        s[l][r] = "(" + s[l][id] + s[id + 1][r] + ")";
    }

    void solve() {
        int n = nextInt();
        a = new Pair[n];
        for (int i = 0; i < n; ++i) {
            a[i] = new Pair(nextInt(), nextInt());
        }
        dp = new long[n][n];
        s = new String[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(dp[i], Long.MAX_VALUE);
            Arrays.fill(s[i], "");
        }
        rec(0, n - 1);
       // out.println(dp[0][n - 1]);
        out.println(s[0][n - 1]);
    }

    class Pair {
        long x, y;

        Pair(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
}
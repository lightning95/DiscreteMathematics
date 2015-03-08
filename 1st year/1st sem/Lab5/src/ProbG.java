import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbG {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbG().run();
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
        String name = "palindrome.";
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
        String s = nextToken();
        char[] a = s.toCharArray();
        char[] b = new StringBuilder(s).reverse().toString().toCharArray();
        int n = a.length;
        int[][] dp = new int[n + 1][n + 1];
        Pair[][] prev = new Pair[n + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            Arrays.fill(prev[i], new Pair(-1, -1));
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (a[i - 1] == b[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    prev[i][j] = new Pair(i - 1, j - 1);
                } else if (dp[i - 1][j] > dp[i][j - 1]) {
                    dp[i][j] = dp[i - 1][j];
                    prev[i][j] = new Pair(i - 1, j);
                } else {
                    dp[i][j] = dp[i][j - 1];
                    prev[i][j] = new Pair(i, j - 1);
                }
            }
        }
        StringBuilder ans = new StringBuilder();
        for (Pair cur = new Pair(n, n), pr; cur.x != 0 && cur.y != 0; cur = pr) {
            pr = prev[cur.x][cur.y];
            if (cur.x - pr.x + cur.y - pr.y > 1) {
                ans.append(a[cur.x - 1]);
            }
        }
        ans = ans.reverse();
        out.println(ans.length());
        out.println(ans.toString());
    }

    class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
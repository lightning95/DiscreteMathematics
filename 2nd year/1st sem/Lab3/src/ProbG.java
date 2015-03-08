import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 10/17/14.
 */

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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "planaritycheck";
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

    boolean checkK5(int[][] a) {
        int n = a.length;
        for (int g = 0; g < n; ++g) {
            int cnt = 0;
            int u = -1;
            int v = -1;
            for (int j = 0; j < n; ++j) {
                cnt += a[g][j];
                u = u == -1 && a[g][j] == 1 ? j : u;
                v = a[g][j] == 1 ? j : v;
            }
            if (cnt == 2) {
                int c = a[u][v] == 0 ? 1 : 0;
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < i; ++j) {
                        c += i != g && j != g ? a[i][j] : 0;
                    }
                }
                if (c >= 10) {
                    return false;
                }
            }
        }
        for (int g = 0; g < n; ++g) {
            int c = 0;
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < i; ++j) {
                    c += i != g && j != g ? a[i][j] : 0;
                }
            }
            if (c >= 10) {
                return false;
            }
        }
        return true;
    }

    boolean checkK33(int[][] a) {
        int n = a.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = j + 1; k < n; ++k) {
                    int cnt = 0;
                    for (int t = 0; t < n; ++t) {
                        cnt += t != i && t != j && t != k && a[i][t] + a[j][t] + a[k][t] == 3 ? 1 : 0;
                    }
                    if (cnt >= 3) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    void solve() {
        int t = nextInt();
        for (int tId = 0; tId < t; ++tId) {
            String s = nextToken();
            int n = 1;
            for (int i = 1; i < 10; ++i) {
                if (i * (i - 1) / 2 == s.length()) {
                    n = i;
                }
            }
            int[][] a = new int[n][n];
            int cnt = 0;
            for (int i = 1, k = 0; i < n; ++i) {
                for (int j = 0; j < i; ++j) {
                    cnt += a[i][j] = a[j][i] = (s.charAt(k++) == '1') ? 1 : 0;
                }
            }
            out.println(n < 5 || n == 5 && cnt <= 9 || n == 6 && cnt <= 12 && checkK5(a) && checkK33(a) ? "YES" : "NO");
        }
    }
}
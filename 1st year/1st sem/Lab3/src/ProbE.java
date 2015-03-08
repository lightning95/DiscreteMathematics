import java.io.*;
import java.util.StringTokenizer;

public class ProbE {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
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
        try {
            File f = new File("num2brackets.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("num2brackets.out");
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
        int n = nextInt();
        long k = nextLong();

        long[][] c = new long[n * 2 + 1][n + 1];
        c[0][0] = 1;
        for (int i = 0; i < n * 2; ++i) {
            for (int j = 0; j <= n; ++j) {
                if (j > 0) {
                    c[i + 1][j - 1] += c[i][j];
                }
                if (j < n) {
                    c[i + 1][j + 1] += c[i][j];
                }
            }
        }

        for (int i = 1, bal = 0; i <= n * 2; ++i) {
            if (bal < n && c[n * 2 - i][bal + 1] > k) {
                out.print('(');
                ++bal;
            } else {
                if (bal < n) {
                    k -= c[n * 2 - i][bal + 1];
                }
                --bal;
                out.print(')');
            }
        }
    }
}
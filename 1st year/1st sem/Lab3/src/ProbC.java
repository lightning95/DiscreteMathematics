import java.io.*;
import java.util.StringTokenizer;

public class ProbC {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbC().run();
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
            File f = new File("num2choose.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("num2choose.out");
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
        int k = nextInt();
        int m = nextInt();

        int[][] c = new int[n + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            c[i][0] = 1;
        }


        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                c[i][j] = c[i - 1][j - 1] + c[i - 1][j];
            }
        }

        for (int i = 0, last = 0; i < k; ++i) {
            for (++last; last <= n; ++last) {
                if (c[n - last][k - i - 1] > m) {
                    out.print(last + " ");
                    break;
                } else {
                    m -= c[n - last][k - i - 1];
                }
            }
        }
    }
}
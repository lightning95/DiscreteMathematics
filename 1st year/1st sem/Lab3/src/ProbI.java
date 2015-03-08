
import java.io.*;
import java.util.StringTokenizer;

public class ProbI {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
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
        try {
            File f = new File("num2part.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("num2part.out");
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
        long[][] c = new long[n + 1][n + 1];

        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j < i; ++j) {
                for (int l = j; l <= i - j; ++l) {
                    c[i][j] += c[i - j][l];
                }
            }
            c[i][i] = 1;
        }
        String s = "";
        for (int i = n, j = 1; i > 0; ) {
            if (c[i][j] > k) {
                s += j + "+";
                i -= j;
            } else {
                k -= c[i][j];
                ++j;
            }
        }
        out.println(s.substring(0, s.length() - 1));
    }
}
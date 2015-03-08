import java.io.*;
import java.util.StringTokenizer;

public class ProbF {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

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
        try {
            File f = new File("subsets.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("subsets.out");
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    void rec(int n, int k, int last, String s) {
        out.println(s);

        if (k > 0) {
            for (int i = last + 1; i <= n; ++i) {
                if (i > last) {
                    rec(n, k - 1, i, s + i + " ");
                }
            }
        }
    }

    void solve() {
        int n = nextInt();
        rec(n, n, 0, "");
    }
}
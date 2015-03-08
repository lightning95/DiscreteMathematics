import java.io.*;
import java.util.StringTokenizer;

public class ProbJ {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

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
            File f = new File("part2num.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("part2num.out");
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
        int[] a = new int[100];
        int n = 0, p = 0;
        for (int l = 0; l < s.length(); ) {
            int r = l;
            for (; r < s.length() && Character.isDigit(s.charAt(r)); ++r) {

            }
            a[p] = Integer.valueOf(s.substring(l, r));
            n += a[p++];
            l = r + 1;
        }

        long[][] c = new long[n + 1][n + 1];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j < i; ++j) {
                for (int l = j; l <= i - j; ++l) {
                    c[i][j] += c[i - j][l];
                }
            }
            c[i][i] = 1;
        }

        long ans = 0;
        for (int i = 0, last = 1; i < p; ++i) {
            if (a[i] != last) {
                for (int j = last; j < a[i]; ++j) {
                    ans += c[n][j];
                }
            }
            n -= a[i];
            last = a[i];
        }
        out.println(ans);
    }
}

import java.io.*;
import java.util.StringTokenizer;

public class ProbB {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbB().run();
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
            File f = new File("perm2num.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("perm2num.out");
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

        long[] fac = new long[n + 1];
        fac[1] = 1;
        for (int i = 2; i <= n; ++i) {
            fac[i] = fac[i - 1] * i;
        }
        boolean[] us = new boolean[n];

        long ans = 0;
        for (int i = 1; i <= n; ++i) {
            int x = nextInt() - 1;
            us[x] = true;
            for (int j = 0; j < x; ++j) {
                if (!us[j]) {
                    ans += fac[n - i];
                }
            }
        }
        out.println(ans);
    }
}
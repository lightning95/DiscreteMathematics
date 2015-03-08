import java.io.*;
import java.util.StringTokenizer;

public class ProbE {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    int[] h = {1, 0, -1, 0};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbE().run();
    }

    String nextToken() {
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

    int nextInt() {
        return Integer.parseInt(nextToken());
    }

    long nextLong() {
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
        String name = "markchain";
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

    double[] count(double[][] a) {
        int n = a.length;
        double[][] q = new double[n][n + 1];
        for (int i = 0; i <= n; ++i) {
            q[0][i] = 1;
        }
        for (int i = 0; i < n - 1; ++i) {
            for (int j = 0; j < n; ++j) {
                q[i + 1][j] = a[j][i];
            }
            --q[i + 1][i];
        }
        for (int i = 0; i < n; ++i) {
            if (q[i][i] != 1) {
                double mul = q[i][i];
                for (int j = 0; j <= n; ++j) {
                    q[i][j] /= mul;
                }
            }
            for (int t = 0; t < n; ++t) {
                if (t != i) {
                    double mul = q[t][i];
                    for (int j = 0; j <= n; ++j) {
                        q[t][j] -= mul * q[i][j];
                    }
                }
            }
        }
        double[] res = new double[n];
        for (int i = 0; i < n; ++i) {
            res[i] = q[i][n];
        }

        return res;
    }

    void solve() {
        int n = nextInt();
        double[][] a = new double[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = nextDouble();
            }
        }
        double[] ans = count(a);
        for (int i = 0; i < n; ++i) {
            out.println(ans[i]);
        }
/*
        boolean ok = true;
        double[] cur = new double[n];

        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < n; ++i) {
                cur[j] += a[i][j] * ans[i];
            }
        }
        for (int i = 0; i < n; ++i) {
            ok = ok && ans[i] == cur[i];
        }
        out.println(ok);*/
    }

    class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
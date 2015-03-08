import java.io.*;
import java.util.StringTokenizer;

public class ProbD {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    int[] h = {1, 0, -1, 0};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbD().run();
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
        String name = "absmarkchain";
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

    void solve() {
        int n = nextInt(), m = nextInt();
        double[][] a = new double[n][n];
        boolean[] abs = new boolean[n];
        for (int i = 0; i < m; ++i) {
            int x = nextInt() - 1, y = nextInt() - 1;
            a[x][y] = nextDouble();
            abs[x] = x == y && a[x][y] == 1.;
        }

        int[] id = new int[n];
        int k = 0, p = 0;
        for (int i = 0; i < n; ++i) {
            if (!abs[i]) {
                id[i] = k++;
            } else {
                id[i] = p++;
            }
        }

        double[][] q = new double[k][k], r = new double[k][p], e = new double[k][k];
        for (int i = 0; i < k; ++i) {
            q[i][i] = 1;
            e[i][i] = 1;
        }
        for (int i = 0; i < n; ++i) {
            if (!abs[i]) {
                for (int j = 0; j < n; ++j) {
                    if (!abs[j]) {
                        q[id[i]][id[j]] -= a[i][j];
                    } else {
                        r[id[i]][id[j]] = a[i][j];
                    }
                }
            }
        }

        for (int i = 0; i < k; ++i) {
            if (q[i][i] != 1) {
                double mul = q[i][i];
                for (int j = 0; j < k; ++j) {
                    q[i][j] /= mul;
                    e[i][j] /= mul;
                }
            }
            for (int t = 0; t < k; ++t) {
                if (t != i) {
                    double mul = q[t][i];
                    for (int j = 0; j < k; ++j) {
                        q[t][j] -= mul * q[i][j];
                        e[t][j] -= mul * e[i][j];
                    }
                }
            }
        }

        double[][] g = new double[k][p];
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < p; ++j) {
                for (int t = 0; t < k; ++t) {
                    g[i][j] += e[i][t] * r[t][j];
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            double ans = 0;
            if (abs[i]) {
                ans++;
                for (int j = 0; j < k; ++j) {
                    ans += g[j][id[i]];
                }
                ans /= n;
            }
            out.println(ans);
        }
    }

    class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
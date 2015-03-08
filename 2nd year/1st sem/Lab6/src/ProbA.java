import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbA {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbA().run();
    }

    String next() {
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

    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    int nextInt() {
        return Integer.parseInt(next());
    }

    void run() throws IOException {
        String name = "assignment";
        InputStream input = System.in;
        OutputStream output = System.out;
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new FileOutputStream(name + ".out");
            }
        } catch (Exception e) {
        }

        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);

        solve();

        br.close();
        out.close();
    }

    void solve() {
        int n = nextInt();
        int[][] a = new int[n + 1][n + 1];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                a[i][j] = nextInt();
            }
        }

        int[] u = new int[n + 1];
        int[] v = new int[n + 1];
        int[] p = new int[n + 1];
        int[] way = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            p[0] = i;
            int j0 = 0;
            int[] minv = new int[n + 1];
            Arrays.fill(minv, Integer.MAX_VALUE);
            boolean[] used = new boolean[n + 1];

            do {
                used[j0] = true;
                int i0 = p[j0];
                int delta = Integer.MAX_VALUE;
                int j1 = 0;
                for (int j = 1; j <= n; ++j) {
                    if (!used[j]) {
                        int cur = a[i0][j] - u[i0] - v[j];
                        if (cur < minv[j]) {
                            minv[j] = cur;
                            way[j] = j0;
                        }
                        if (minv[j] < delta) {
                            delta = minv[j];
                            j1 = j;
                        }
                    }
                }
                for (int j = 0; j <= n; ++j) {
                    if (used[j]) {
                        u[p[j]] += delta;
                        v[j] -= delta;
                    } else {
                        minv[j] -= delta;
                    }
                }
                j0 = j1;
            } while (p[j0] != 0);
            do {
                int j1 = way[j0];
                p[j0] = p[j1];
                j0 = j1;
            } while (j0 != 0);
        }

        out.println(-v[0]);
        int[] ans = new int[n + 1];
        for (int j = 1; j <= n; ++j) {
            ans[p[j]] = j;
        }
        for (int i = 1; i <= n; ++i) {
            out.println(i + " " + ans[i]);
        }
    }
}

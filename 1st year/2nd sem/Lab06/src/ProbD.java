import java.io.*;
import java.util.StringTokenizer;

public class ProbD {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbD().run();
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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "garland";
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + ".out");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    public double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    final double eps = 1e-5;

    int check2(double h, double h1, double h2, int n) {
        for (int i = 2; i < n; ++i) {
            double cur = 2 * h1 + 2 - h;
            h = h1;
            h1 = cur;
        }
        return Math.abs(h1 - h2) < eps ? 0 : h1 < h2 ? -1 : 1;
        //return Double.compare(h1, h2);
    }

    double check(double h, double h2, int n) {
        double l = 0, r = Integer.MAX_VALUE;
        while (r - l > eps) {
            double m = (l + r) / 2;
            int res = check2(h, m, h2, n);
            if (res == 0) {
                return m;
            }
            if (res > 0) {
                r = m;
            } else {
                l = m;
            }
        }
        return l;
    }

    void solve() {
        int n = nextInt();
        double h = nextDouble();
        double l = 0, r = Integer.MAX_VALUE;
        double ans = Integer.MAX_VALUE;
        while (r - l > eps) {
            double m = (l + r) / 2;
            double h1 = h, h2 = check(h, m, n), min = Math.min(h1, h2);
            for (int i = 2; i < n; ++i) {
                double cur = 2 * h2 + 2 - h1;
                h1 = h2;
                h2 = cur;
                min = Math.min(min, cur);
            }
            if (min > 0) {
                r = m;
                ans = Math.min(m, ans);
            } else {
                l = m;
            }
        }
        out.printf("%.2f\n", ans);
    }
}
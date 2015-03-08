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
        String name = "spantree";
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
        Pair[] a = new Pair[n];
        for (int i = 0; i < n; ++i) {
            a[i] = new Pair(nextInt(), nextInt());
        }

        double[] d = new double[n];
        Arrays.fill(d, Double.MAX_VALUE);
        d[0] = 0;
        boolean[] us = new boolean[n];

        double ans = 0;

        for (; ; ) {
            int id = -1;
            for (int i = 0; i < n; ++i) {
                if (!us[i] && (id == -1 || d[id] > d[i])) {
                    id = i;
                }
            }
            if (id == -1) {
                break;
            }
            us[id] = true;
            ans += d[id];
            for (int i = 0; i < n; ++i) {
                if (i != id) {
                    d[i] = Math.min(d[i], Pair.distanceTo(a[i], a[id]));
                }
            }
        }

        out.println(ans);
    }

    static class Pair {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        static double distanceTo(Pair p, Pair t) {
            return Math.sqrt((1. * t.x - p.x) * (1. * t.x - p.x) + (1. * t.y - p.y) * (1. * t.y - p.y));
        }
    }
}

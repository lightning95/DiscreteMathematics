import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbC {
    boolean eof;
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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "crypto";
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

    void solve() {
        int p = nextInt(), n = nextInt(), m = nextInt();
        mod = p;
        Matrix[] a = new Matrix[n];

        for (int i = 0; i < n; ++i) {
            a[i] = new Matrix(nextInt(), nextInt(), nextInt(), nextInt());
        }
        SegmentTree tree = new SegmentTree(a);
        for (int i = 0; i < m; ++i) {
            out.println(tree.findMul(1, tree.sh, (tree.sh << 1) - 1, tree.sh + nextInt() - 1, tree.sh + nextInt() - 1));
        }
    }

    static int mod;

    public static class Matrix {
        int x00, x01, x10, x11;

        static final Matrix E = new Matrix(1, 0, 0, 1);

        public Matrix(int x00, int x01, int x10, int x11) {
            this.x00 = x00 % mod;
            this.x01 = x01 % mod;
            this.x10 = x10 % mod;
            this.x11 = x11 % mod;
        }

        public Matrix multiply(Matrix a) {
            return a == E ? this : new Matrix(x00 * a.x00 + x01 * a.x10,
                    x00 * a.x01 + x01 * a.x11,
                    x10 * a.x00 + x11 * a.x10,
                    x10 * a.x01 + x11 * a.x11);
        }

        public String toString() {
            return x00 + " " + x01 + "\n" + x10 + " " + x11 + "\n";
        }
    }

    class SegmentTree {
        private Matrix[] t;
        int sh;

        SegmentTree(Matrix[] a) {
            sh = Math.max(1, Integer.highestOneBit(a.length - 1) << 1);
            t = new Matrix[sh << 1];
            Arrays.fill(t, Matrix.E);
            for (int i = sh; i < sh + a.length; ++i) {
                t[i] = a[i - sh];
            }
            for (int i = sh - 1; i >= 0; --i) {
                t[i] = t[i << 1].multiply(t[(i << 1) + 1]);
            }
        }

        public Matrix findMul(int x, int l, int r, int a, int b) {
            if (r < a || b < l) {
                return Matrix.E;
            }
            if (a <= l && r <= b) {
                return t[x];
            }
            return findMul(x << 1, l, (l + r) >> 1, a, b).multiply(
                    findMul((x << 1) + 1, ((l + r) >> 1) + 1, r, a, b));
        }
    }
}
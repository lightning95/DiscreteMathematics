import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbB {
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
        String name = "rsq";
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
        int n = nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n; ++i) {
            a[i] = nextLong();
        }
        SegmentTree tree = new SegmentTree(a);
        for (String s = nextToken(); !eof; s = nextToken()) {
            if (s.equals("set")) {
                tree.set(nextInt() - 1, nextLong());
            } else if (s.equals("sum")) {
                out.println(tree.findSum(1, tree.sh, (tree.sh << 1) - 1, tree.sh + nextInt() - 1, tree.sh + nextInt() - 1));
            }
        }
    }

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    class SegmentTree {
        private long[] t;
        int sh;

        SegmentTree(long[] a) {
            sh = Math.max(1, Integer.highestOneBit(a.length - 1) << 1);
            t = new long[sh << 1];
            Arrays.fill(t, 0);
            for (int i = sh; i < sh + a.length; ++i) {
                t[i] = a[i - sh];
            }
            for (int i = sh - 1; i >= 0; --i) {
                t[i] = t[i << 1] + t[(i << 1) + 1];
            }
        }

        public long findSum(int x, int l, int r, int a, int b) {
            if (r < a || b < l) {
                return 0;
            }
            if (a <= l && r <= b) {
                return t[x];
            }
            return findSum(x << 1, l, (l + r) >> 1, a, b) +
                    findSum((x << 1) + 1, ((l + r) >> 1) + 1, r, a, b);
        }

        public void set(int id, long x) {
            id += sh;
            t[id] = x;
            while (id > 1) {
                id >>= 1;
                t[id] = t[(id << 1) + 1] + t[id << 1];
            }
        }
    }
}
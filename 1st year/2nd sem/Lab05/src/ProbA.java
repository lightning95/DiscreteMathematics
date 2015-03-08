import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbA {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbA().run();
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
        String name = "rmq";
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
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = nextInt();
        }
        SegmentTree tree = new SegmentTree(a);
        for (String s = nextToken(); !eof; s = nextToken()) {
            if (s.equals("set")) {
                tree.set(nextInt() - 1, nextInt());
            } else if (s.equals("min")) {
                out.println(tree.findMin(1, tree.sh, tree.sh * 2 - 1, tree.sh + nextInt() - 1, tree.sh + nextInt() - 1));
            }
        }
    }

    class SegmentTree {
        private int[] t;
        int sh;

        SegmentTree(int[] a) {
            sh = Math.max(1, Integer.highestOneBit(a.length - 1) << 1);
            t = new int[sh << 1];
            Arrays.fill(t, Integer.MAX_VALUE);
            for (int i = sh; i < sh + a.length; ++i) {
                t[i] = a[i - sh];
            }
            for (int i = sh - 1; i >= 0; --i) {
                t[i] = Math.min(t[i * 2], t[i * 2 + 1]);
            }
        }

        public int findMin(int x, int l, int r, int a, int b) {
            if (r < a || b < l) {
                return Integer.MAX_VALUE;
            }
            if (a <= l && r <= b) {
                return t[x];
            }
            return Math.min(findMin(x * 2, l, (l + r) / 2, a, b),
                    findMin(x * 2 + 1, (l + r) / 2 + 1, r, a, b));
        }

        public void set(int id, int x) {
            id += sh;
            t[id] = x;
            while (id > 1) {
                id >>= 1;
                t[id] = Math.min(t[id * 2 + 1], t[id << 1]);
            }
        }
    }
}
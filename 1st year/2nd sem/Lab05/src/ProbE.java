import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbE {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbE().run();
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
        String name = "parking";
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
        int n = nextInt(), m = nextInt();
        SegmentTree tree = new SegmentTree(n);
        for (int i = 0; i < m; ++i) {
            String s = nextToken();
            int pos = nextInt() - 1;
            if (s.equals("exit")) {
                tree.set(pos, pos);
            } else if (s.equals("enter")) {
                int res = tree.findMin(1, tree.sh, (tree.sh << 1) - 1, pos + tree.sh, tree.sh + n - 1);
                if (res == Integer.MAX_VALUE){
                    res = tree.findMin(1, tree.sh, (tree.sh << 1) - 1, tree.sh, tree.sh + pos - 1);
                }
                out.println(res + 1);
                tree.set(res, Integer.MAX_VALUE);
            }
        }
    }

    class SegmentTree {
        private int[] t;
        int sh;

        SegmentTree(int n) {
            sh = Math.max(1, Integer.highestOneBit(n - 1) << 1);
            t = new int[sh << 1];
            Arrays.fill(t, Integer.MAX_VALUE);
            for (int i = sh; i < sh + n; ++i) {
                t[i] = i - sh;
            }
            for (int i = sh - 1; i >= 0; --i) {
                t[i] = Math.min(t[i << 1], t[(i << 1) + 1]);
            }
        }

        public int findMin(int x, int l, int r, int a, int b) {
            if (r < a || b < l) {
                return Integer.MAX_VALUE;
            }
            if (a <= l && r <= b) {
                return t[x];
            }
            return Math.min(findMin(x << 1, l, (l + r) >> 1, a, b),
                    findMin((x << 1) + 1, ((l + r) >> 1) + 1, r, a, b));
        }

        public void set(int id, int x) {
            id += sh;
            t[id] = x;
            while (id > 1) {
                id >>= 1;
                t[id] = Math.min(t[(id << 1) + 1], t[id << 1]);
            }
        }
    }
}
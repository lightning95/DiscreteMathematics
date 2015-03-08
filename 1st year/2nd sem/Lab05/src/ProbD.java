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
        String name = "rmq2";
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

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    void solve() {
        int n = nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n; ++i) {
            a[i] = nextLong();
        }
        SegmentTree tree = new SegmentTree(a);
        for (String s = nextToken(); !eof; s = nextToken()) {
            int l = nextInt() - 1 + tree.sh, r = nextInt() - 1 + tree.sh;
            if (s.equals("set")) {
                tree.set(1, tree.sh, (tree.sh << 1) - 1, l, r, nextLong());
            } else if (s.equals("min")) {
                out.println(tree.findMin(1, tree.sh, (tree.sh << 1) - 1, l, r));
            } else if (s.equals("add")) {
                tree.add(1, tree.sh, (tree.sh << 1) - 1, l, r, nextLong());
            }
        }
    }

    class SegmentTree {
        class Item {
            long value;
            Long set;
            Long add;

            Item(long value) {
                this.value = value;
            }

            void set(long s) {
                set = s;
                value = s;
                add = null;
            }

            void add(long a) {
                if (set != null) {
                    set += a;
                } else if (add != null) {
                    add += a;
                } else {
                    add = a;
                }
                value += a;
            }
        }

        private Item[] t;
        int sh;

        SegmentTree(long[] a) {
            sh = Math.max(1, Integer.highestOneBit(a.length - 1) << 1);
            t = new Item[sh << 1];
            for (int i = 0; i < t.length; ++i) {
                t[i] = new Item(Long.MAX_VALUE);
            }
            for (int i = sh; i < sh + a.length; ++i) {
                t[i] = new Item(a[i - sh]);
            }
            for (int i = sh - 1; i >= 0; --i) {
                t[i].value = Math.min(t[i << 1].value, t[(i << 1) + 1].value);
            }
        }

        public long findMin(int x, int l, int r, int a, int b) {
            push(x);
            if (r < a || b < l) {
                return Long.MAX_VALUE;
            }
            if (a <= l && r <= b) {
                return t[x].value;
            }
            long left = findMin(x << 1, l, (l + r) >> 1, a, b);
            long right = findMin((x << 1) + 1, ((l + r) >> 1) + 1, r, a, b);
            t[x].value = Math.min(t[x << 1].value, t[(x << 1) + 1].value);
            return Math.min(left, right);
        }

        public void set(int x, int l, int r, int a, int b, long set) {
            push(x);
            if (r < a || b < l) {
                return;
            }
            if (a <= l && r <= b) {
                t[x].set(set);
                push(x);
                return;
            }
            set(x << 1, l, (l + r) >> 1, a, b, set);
            set((x << 1) + 1, ((l + r) >> 1) + 1, r, a, b, set);
            t[x].value = Math.min(t[x << 1].value, t[(x << 1) + 1].value);
        }

        public void add(int x, int l, int r, int a, int b, long add) {
            push(x);
            if (r < a || b < l) {
                return;
            }
            if (a <= l && r <= b) {
                t[x].add(add);
                push(x);
                return;
            }
            add(x << 1, l, (l + r) >> 1, a, b, add);
            add((x << 1) + 1, ((l + r) >> 1) + 1, r, a, b, add);
            t[x].value = Math.min(t[x << 1].value, t[(x << 1) + 1].value);
        }

        private void push(int x) {
            if (t[x].add != null) {
                if (x < sh) {
                    t[x << 1].add(t[x].add);
                    t[(x << 1) + 1].add(t[x].add);
                }
                t[x].add = null;
            }
            if (t[x].set != null) {
                if (x < sh) {
                    t[x << 1].set(t[x].set);
                    t[(x << 1) + 1].set(t[x].set);
                }
                t[x].set = null;
            }
        }
    }
}
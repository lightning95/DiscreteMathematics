import java.io.*;
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

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "bstsimple";
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

    void solve() {
        Node tree = Node.NULL;
        for (String s = nextToken(); !eof; s = nextToken()) {
            if (s.startsWith("i")) {
                if (tree != Node.NULL) {
                    tree.insert(nextInt());
                } else {
                    tree = new Node(nextInt());
                }
            } else if (s.startsWith("d")) {
                tree.delete(nextInt());
            } else if (s.startsWith("e")) {
                out.println(tree.exists(nextInt()));
            } else if (s.startsWith("p")) {
                int res = tree.prev(nextInt());
                out.println(res == Integer.MIN_VALUE ? "none" : res);
            } else {
                int res = tree.next(nextInt());
                out.println(res == Integer.MAX_VALUE ? "none" : res);
            }
        }
    }

    static class Node {
        int x;
        static final Node NULL = new Node();
        Node l = NULL, r = NULL;

        Node(int x) {
            this.x = x;
        }

        Node() {

        }

        boolean exists(int key) {
            if (this.equals(NULL)) {
                return false;
            }

            return x > key ? l.exists(key) : x == key ? true : r.exists(key);
        }

        int next(int key) {
            if (this == NULL) {
                return Integer.MAX_VALUE;
            }
            return x > key ? Math.min(x, l.next(key)) : r.next(key);
        }

        int prev(int key) {
            if (this == NULL) {
                return Integer.MIN_VALUE;
            }
            return x < key ? Math.max(x, r.prev(key)) : l.prev(key);
        }

        void insert(int key) {
            if (this == NULL) {
                x = key;
            }
            if (key < x) {
                if (l == NULL) {
                    l = new Node(key);
                } else {
                    l.insert(key);
                }
            } else if (x < key) {
                if (r == NULL) {
                    r = new Node(key);
                } else {
                    r.insert(key);
                }
            }
        }

        boolean delete(int key) {
            if (this == NULL) {
                return false;
            }

            if (x == key) {
                int res = r.next(key);
                if (res == Integer.MIN_VALUE) {
                    if (l == NULL) {
                        return true;
                    } else {
                        x = l.x;
                        l = l.l;
                        r = l.r;
                    }
                } else {
                    x = res;
                    r.delete(res);
                }
            } else if (key < x) {
                if (l.delete(key)) {
                    l = NULL;
                }
            } else {
                if (r.delete(key)) {
                    r = NULL;
                }
            }
            return false;
        }
    }
}
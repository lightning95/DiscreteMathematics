import java.io.*;
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

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "movetofront";
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
        Node tree = new Node(1);
        for (int i = 1, n = nextInt(); i < n; ++i) {
            tree = tree.merge(new Node(i + 1));
        }
        for (int i = 0, m = nextInt(); i < m; ++i) {
            int l = nextInt(), r = nextInt();
            Node[] sp = tree.split(l);
            Node[] sp2 = sp[1].split(r - sp[0].size + 1);
            tree = sp2[0].merge(sp[0]).merge(sp2[1]);
        }
        tree.print();
        out.println(tree.printer.toString());
    }

    static class Node {
        int d, size;
        static final Node NULL = new Node();
        static final StringBuilder printer = new StringBuilder();

        Node l = NULL, r = NULL, p = NULL;

        Node(int data) {
            d = data;
            size = 1;
        }

        Node() {

        }

        private void refresh() {
            size = l.size + r.size + 1;
        }

        private Node find(int key) {
            if (this == NULL) {
                return NULL;
            }
            if (l.size + 1 == key) {
                splay();
                return this;
            } else if (l.size + 1 < key) {
                return r.find(key - l.size - 1);
            } else {
                return l.find(key);
            }
        }

        private void turn() {
            Node p = this.p;
            this.p = p.p;
            if (p.p != NULL) {
                if (p.p.l == p) {
                    p.p.setLeft(this);
                } else {
                    p.p.setRight(this);
                }
            }
            if (p.l == this) { // this - left son of p
                p.setLeft(r);
                setRight(p);
            } else {
                p.setRight(l);
                setLeft(p);
            }
        }

        private void setLeft(Node u) {
            l = u;
            if (u != NULL) {
                u.p = this;
            }
            refresh();
        }

        private void setRight(Node u) {
            r = u;
            if (u != NULL) {
                u.p = this;
            }
            refresh();
        }

        private void splay() {
            while (p != NULL) {
                if (p.p == NULL) {
                    // ZIG
                    turn();
                } else if (this == p.l && p.p.l == p || this == p.r && p.p.r == p) {
                    // ZIG - ZIG
                    p.turn();
                    turn();
                } else {
                    // ZIG - ZAG
                    turn();
                    turn();
                }
            }
        }

        Node[] split(int key) {
            if (key > size) {
                return new Node[]{this, NULL};
            }
            Node root = find(key);
            Node t = root.l;
            root.setLeft(NULL);
            if (t != NULL) {
                t.p = NULL;
            }
            return new Node[]{t, root};
        }

        Node merge(Node n) {
            Node root = find(size);
            root.setRight(n);
            return root;
        }

        void print() {
            if (this == NULL) {
                return;
            }
            l.print();
            printer.append(d + " ");
            r.print();
        }
    }
}
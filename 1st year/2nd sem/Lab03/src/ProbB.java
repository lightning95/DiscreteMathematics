import java.io.*;
import java.nio.file.NotDirectoryException;
import java.util.Random;
import java.util.StringTokenizer;

public class ProbB {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;


    public static void main(String[] args) throws IOException {
        new ProbB().run();
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
        String name = "bst";
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
                    tree = tree.insert(tree, nextInt());
                } else {
                    tree = new Node(nextInt());
                }
            } else if (s.startsWith("d")) {
                tree = tree.delete(tree, nextInt());
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
        int x, y;
        static final Node NULL = new Node();
        Node l = NULL, r = NULL;
        static final Random rand = new Random();

        Node(int x) {
            this.x = x;
            y = rand.nextInt();
        }

        Node() {

        }

        boolean exists(int key) {
            if (this == NULL) {
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

        Node insert(Node n, int key) {
            Node[] t = split(n, key);
            return merge(merge(t[0], new Node(key)), t[1]);
        }

        Node delete(Node n, int key) {
            Node[] t = split(n, key);
            return merge(t[0], t[1]);
        }

        Node[] split(Node n, int key) {
            if (n == NULL) {
                return new Node[]{NULL, NULL};
            }
            if (n.x == key) {
                return new Node[]{n.l, n.r};
            } else if (n.x < key) {
                Node[] t = split(n.r, key);
                n.r = NULL;
                return new Node[]{merge(n, t[0]), t[1]};
            } else {
                Node[] t = split(n.l, key);
                n.l = NULL;
                return new Node[]{t[0], merge(t[1], n)};
            }
        }

        Node merge(Node n1, Node n2) {
            if (n1 == NULL) {
                return n2;
            }
            if (n2 == NULL) {
                return n1;
            }
            if (n1.y < n2.y) {
                n1.r = merge(n1.r, n2);
                return n1;
            } else {
                n2.l = merge(n1, n2.l);
                return n2;
            }
        }
    }
}
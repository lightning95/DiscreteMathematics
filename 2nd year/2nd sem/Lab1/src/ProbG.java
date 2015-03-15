import java.io.*;
import java.util.StringTokenizer;

public class ProbG {
    RW rw;

    class RW {
        private boolean eof;
        StringTokenizer st;
        PrintWriter out;
        BufferedReader br;

        RW(String inputFile, String outputFile) {
            br = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(new OutputStreamWriter(System.out));

            File f = new File(inputFile);
            if (f.exists() && f.canRead()) {
                try {
                    br = new BufferedReader(new FileReader(inputFile));
                    out = new PrintWriter(new FileWriter(outputFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    eof = true;
                    return "-1";
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        void println() {
            out.println();
        }

        void println(Object o) {
            out.println(o);
        }

        void print(Object o) {
            out.print(o);
        }

        void close() {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }

    public static void main(String[] args) {
        new ProbG().run();
    }

    private void run() {
        rw = new RW("search4.in", "search4.out");
        solve();
        rw.close();
    }

    class Vertex {
        static final int size = 26;

        Vertex[] next;
        Vertex[] go;
        Vertex p;
        Vertex link;
        Vertex nLeaf;
        int pCh;
        int id = -1;

        Vertex(boolean root) {
            if (root) {
                p = this;
                link = this;
                nLeaf = this;
            }
            next = new Vertex[size];
            go = new Vertex[size];
        }

        Vertex(Vertex f, int c) {
            this(false);
            p = f;
            pCh = c;
        }

        boolean isRoot() {
            return this.equals(this.p);
        }
    }

    private void addString(Vertex root, String s, int leaf) {
        Vertex cur = root;
        for (char c : s.toCharArray()) {
            int id = c - 'a';
            if (cur.next[id] == null) {
                cur.next[id] = new Vertex(cur, id);
            }
            cur = cur.next[id];
        }
        cur.id = leaf;
    }

    Vertex getLink(Vertex v) {
        if (v.link == null) {
            if (v.isRoot() || v.p.isRoot()) {
                v.link = v.p;
            } else {
                v.link = go(getLink(v.p), v.pCh);
            }
        }
        return v.link;
    }

    private Vertex go(Vertex v, int c) {
        if (v.go[c] == null) {
            if (v.next[c] != null) {
                v.go[c] = v.next[c];
            } else {
                v.go[c] = v.isRoot() ? v : go(getLink(v), c);
            }
        }
        return v.go[c];
    }

    Vertex findNearestLeaf(Vertex cur) {
        if (cur.nLeaf == null) {
            cur.nLeaf = findNearestLeaf(getLink(cur));
            if (cur.id >= 0) {
                return cur;
            }
        }
        return cur.nLeaf;
    }

    private void solve() {
        int n = rw.nextInt();
        boolean[] ans = new boolean[n];

        Vertex root = new Vertex(true);
        for (int i = 0; i < n; ++i) {
            String s = rw.next();
            addString(root, s, i);
        }

        char[] text = rw.next().toCharArray();
        Vertex cur = root;
        for (char c : text) {
            int id = c - 'a';
            cur = go(cur, id);

            Vertex leaf = findNearestLeaf(cur);
            while (leaf.id >= 0 && !ans[leaf.id]) {
                ans[leaf.id] = true;
                leaf = leaf.nLeaf;
            }
        }
        for (boolean b : ans) {
            rw.println(b ? "YES" : "NO");
        }
    }
}

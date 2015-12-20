import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ProbC {
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
        new ProbC().run();
    }

    private void run() {
        rw = new RW("tree.in", "tree.out");
        solve();
        rw.close();
    }

    char[] s;
    int n, MAXN = 100_100;

    class Node {
        int l, r, par, link;
        Map<Character, Integer> next;

        Node() {
            l = 0;
            r = 0;
            par = -1;
            next = new HashMap<>();
        }

        Node(int l, int r, int par) {
            this();
            this.l = l;
            this.r = r;
            this.par = par;
        }

        int len() {
            return r - l;
        }

        int get(char c) {
            if (!next.containsKey(c)) {
                next.put(c, -1);
            }
            return next.get(c);
        }

        void put(char c, int id) {
            next.put(c, id);
        }
    }

    Node[] t;
    int sz;

    class State {
        int v, pos;

        State(int v, int pos) {
            this.v = v;
            this.pos = pos;
        }
    }

    State ptr = new State(0, 0);

    State go(State st, int l, int r) {
        while (l < r) {
            if (st.pos == t[st.v].len()) {
                st = new State(t[st.v].get(s[l]), 0);
                if (st.v == -1) {
                    return st;
                }
            } else {
                if (s[t[st.v].l + st.pos] != s[l]) {
                    return new State(-1, -1);
                }
                if (r - l < t[st.v].len() - st.pos) {
                    return new State(st.v, st.pos + r - l);
                }
                l += t[st.v].len() - st.pos;
                st.pos = t[st.v].len();
            }
        }
        return st;
    }

    int split(State st) {
        if (st.pos == t[st.v].len()) {
            return st.v;
        }
        if (st.pos == 0) {
            return t[st.v].par;
        }
        Node v = t[st.v];
        int id = sz++;
        t[id] = new Node(v.l, v.l + st.pos, v.par);

        t[v.par].put(s[v.l], id);
        t[id].put(s[v.l + st.pos], st.v);
        t[st.v].par = id;
        t[st.v].l += st.pos;
        return id;
    }

    int get_link(int v) {
        if (t[v].link != -1) {
            return t[v].link;
        }
        if (t[v].par == -1) {
            return 0;
        }
        int to = get_link(t[v].par);
        return t[v].link = split(go(new State(to, t[to].len()), t[v].l + (t[v].par == 0 ? 1 : 0), t[v].r));
    }

    void tree_extend(int pos) {
        for (; ; ) {
            State nptr = go(ptr, pos, pos + 1);
            if (nptr.v != -1) {
                ptr = nptr;
                return;
            }

            int mid = split(ptr);
            int leaf = sz++;
            t[leaf] = new Node(pos, n, mid);
            t[mid].put(s[pos], leaf);

            ptr.v = get_link(mid);
            ptr.pos = t[ptr.v].len();
            if (mid == 0) {
                break;
            }
        }
    }

    private void solve() {
        String ss = rw.next();
        s = ss.toCharArray();
        sz = 1;
        n = s.length;
        t = new Node[MAXN];
        t[0] = new Node();
        for (int i = 0; i < n; ++i) {
            tree_extend(i);
        }

        dfs(0);
    }

    private void dfs(int id) {
        if (id != 0) {
            rw.println((t[id].par + 1) + " " + (id + 1) + " " + (t[id].l + 1) + " " + (t[id].r));
        }
        for (char c = 'a'; c <= 'z'; ++c) {
            if (t[id].next.containsKey(c)) {
                dfs(t[id].next.get(c));
            }
        }
    }
}

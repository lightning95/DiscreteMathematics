import java.io.*;
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
        rw = new RW("trie.in", "trie.out");
        solve();
        rw.close();
    }

    int gId = 0;

    class Vertex {
        Vertex[] go;
        int id;

        Vertex() {
            this.id = ++gId;
        }

        Vertex add(int c) {
            if (go == null) {
                go = new Vertex[26];
            }
            if (go[c] == null) {
                go[c] = new Vertex();
            }
            return go[c];
        }
    }

    void dfs(Vertex v) {
        for (int i = 0; v.go != null && i < v.go.length; ++i) {
            if (v.go[i] == null) {
                continue;
            }
            rw.println(v.id + " " + v.go[i].id + " " + (char)(i + 'a'));
            dfs(v.go[i]);
        }
    }

    private void solve() {
        String s = rw.next();
        Vertex root = new Vertex();

        for (int i = 0; i < s.length(); ++i) {
            Vertex cur = root;
            for (char c : s.substring(i).toCharArray()) {
                cur = cur.add(c - 'a');
            }
        }

        rw.println(gId + " " + (gId - 1));
        dfs(root);
    }
}

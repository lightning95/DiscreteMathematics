import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */
public class ProbB {
    RW rw;
    String FILE_NAME = "problem2";

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
        new ProbB().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    class Edge {
        int u;
        int v;
        char c;

        Edge(int u, int v, char c) {
            this.u = u;
            this.v = v;
            this.c = c;
        }
    }

    boolean[] term;
    List<Edge>[] edges;
    int[][] was;

    private void solve() {
        String w = rw.next();
        int n = rw.nextInt();
        int m = rw.nextInt();
        int k = rw.nextInt();
        term = new boolean[n ];
        for (int i = 0; i < k; ++i) {
            term[rw.nextInt() - 1] = true;
        }
        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
        was = new int[n][w.length() + 1];
        for (int i = 0; i < m; ++i) {
            int u = rw.nextInt() - 1;
            int v = rw.nextInt() - 1;
            char c = rw.next().toCharArray()[0];
            edges[u].add(new Edge(u, v, c));
        }
        rec(0, 0, w);
        rw.println(was[0][0] == 2 ? "Accepts" : "Rejects");
    }

    private void rec(int u, int id, String w) {
        if (was[u][id] != 0) {
            return;
        }
        if (id == w.length()) {
            was[u][id] = term[u] ? 2 : 1;
            return;
        }
        was[u][id] = 1;
        for (Edge e : edges[u]) {
            if (e.c == w.charAt(id)) {
                rec(e.v, id + 1, w);
                if (was[e.v][id + 1] == 2) {
                    was[u][id] = 2;
                    break;
                }
            }
        }
    }
}

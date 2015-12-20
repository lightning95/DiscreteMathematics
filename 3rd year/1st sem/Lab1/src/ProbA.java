import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */
public class ProbA {
    RW rw;
    String FILE_NAME = "problem1";

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
        new ProbA().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    private void solve() {
        String w = rw.next();
        int n = rw.nextInt();
        int m = rw.nextInt();
        int k = rw.nextInt();
        boolean[] term = new boolean[n];
        for (int i = 0; i < k; ++i) {
            term[rw.nextInt() - 1] = true;
        }
        int[][] edges = new int[n][26];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(edges[i], -1);
        }
        for (int i = 0; i < m; ++i) {
            int u = rw.nextInt() - 1;
            int v = rw.nextInt() - 1;
            char c = rw.next().toCharArray()[0];
            edges[u][c - 'a'] = v;
        }

        int pos = 0;
        for (int i = 0; pos >= 0 && i < w.length(); i++) {
            pos = edges[pos][w.charAt(i) - 'a'];
        }
        rw.println(pos >= 0 && term[pos] ? "Accepts" : "Rejects");
    }
}

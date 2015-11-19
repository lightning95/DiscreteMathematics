import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */
public class ProbD {
    RW rw;
    String FILE_NAME = "problem4";

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
        new ProbD().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    List<Integer>[] edges;
    int[][] was;
    int[][] d;
    private static final int MOD = 1_000_000_007;

    private void solve() {
        int n = rw.nextInt();
        int m = rw.nextInt();
        int k = rw.nextInt();
        int l = rw.nextInt();
        int[] term = new int[k];
        for (int i = 0; i < k; ++i) {
            int x = rw.nextInt() - 1;
            term[i] = x;
        }

        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
        was = new int[n][l + 1];
        d = new int[n][l + 1];
        for (int i = 0; i < m; ++i) {
            int u = rw.nextInt() - 1;
            int v = rw.nextInt() - 1;
            rw.next();
            edges[u].add(v);
        }
        int ans = 0;
        d[0][0] = 1;
        for (int i = 0; i < l; ++i) {
            for (int u = 0; u < n; ++u) {
                if (d[u][i] > 0) {
                    for (int v : edges[u]) {
                        d[v][i + 1] = (d[v][i + 1] + d[u][i]) % MOD;
                    }
                }
            }
        }
        for (int i : term) {
            ans = (ans + d[i][l]) % MOD;
        }
        rw.println(ans);
    }
}

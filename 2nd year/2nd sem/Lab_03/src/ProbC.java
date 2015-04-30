import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 4/29/15.
 */
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
        rw = new RW("matching.in", "matching.out");
        solve();
        rw.close();
    }

    List<Integer>[] edges;
    int[] pair;
    boolean[] us;

    private void solve() {
        int n = rw.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; ++i) {
            w[i] = rw.nextInt();
        }
        int[] id = new int[n];
        for (int i = 0; i < n; ++i) {
            id[i] = i;
        }
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (w[id[i]] < w[id[j]]) {
                    int t = id[i];
                    id[i] = id[j];
                    id[j] = t;
                }
            }
        }

        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; ++i) {
            int m = rw.nextInt();
            for (int j = 0; j < m; ++j) {
                edges[i].add(rw.nextInt() - 1);
            }
        }

        us = new boolean[n];
        pair = new int[n];
        Arrays.fill(pair, -1);
        for (int i = 0; i < n; ++i) {
            Arrays.fill(us, false);
            dfs(id[i]);
        }
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        for (int i = 0; i < n; ++i) {
            if (pair[i] != -1) {
                ans[pair[i]] = i;
            }
        }
        Arrays.stream(ans).forEach(x -> rw.print(x + 1 + " "));
        rw.println();
    }

    private boolean dfs(int u) {
        us[u] = true;
        for (int v : edges[u]) {
            if (pair[v] == -1 || !us[pair[v]] && dfs(pair[v])) {
                pair[v] = u;
                return true;
            }
        }
        return false;
    }
}

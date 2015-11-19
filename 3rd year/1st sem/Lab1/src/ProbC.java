import java.io.*;
import java.util.*;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */
public class ProbC {
    RW rw;
    String FILE_NAME = "problem3";

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
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    boolean[] isTerm;
    List<Integer>[] edges;
    int[] was;
    int[] prev;
    int[] d;
    List<Integer>[] revEdges;
    private static final int MOD = 1_000_000_007;
    HashSet<Integer> inCycle;

    private void solve() {
        int n = rw.nextInt();
        int m = rw.nextInt();
        int k = rw.nextInt();
        isTerm = new boolean[n];
        int[] term = new int[k];
        for (int i = 0; i < k; ++i) {
            int x = rw.nextInt() - 1;
            isTerm[x] = true;
            term[i] = x;
        }

        edges = new ArrayList[n];
        revEdges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
            revEdges[i] = new ArrayList<>();
        }
        was = new int[n];
        prev = new int[n];
        for (int i = 0; i < m; ++i) {
            int u = rw.nextInt() - 1;
            int v = rw.nextInt() - 1;
            rw.next();
            edges[u].add(v);
            revEdges[v].add(u);
        }
        Arrays.fill(prev, -1);
        inCycle = new HashSet<>();
        check(0, true);
        if (inCycle.size() > 0) {
            Arrays.fill(was, 0);
            inCycle.stream().forEach(u -> check(u, false));
            for (int i = 0; i < k; ++i) {
                if (was[term[i]] > 0) {
                    rw.println(-1);
                    return;
                }
            }
        }
        Arrays.fill(was, 0);
        Arrays.fill(d, 0);
        int ans = 0;
        d[0] = 1;
        for (int i : term) {
            if (was[i] == 0) {
                count(i);
            }
            ans = (ans + d[i]) % MOD;
        }
        rw.println(ans);
    }

    private void count(int u) {
        was[u] = 1;
        for (int v : revEdges[u]) {
            if (was[v] == 0) {
                count(v);
            }
            d[u] = (d[u] + d[v]) % MOD;
        }
    }

    private void check(int u, boolean find) {
        if (was[u] > 0) {
            return;
        }
        was[u] = 1;
        for (int v : edges[u]) {
            if (was[v] == 0) {
                prev[v] = u;
                check(v, find);
            }
            if (was[v] == 1 && find) {
                inCycle.add(v);
            }
        }
        was[u] = 2;
    }
}

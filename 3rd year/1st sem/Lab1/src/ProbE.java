import java.io.*;
import java.util.*;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */
public class ProbE {
    RW rw;
    private static final String FILE_NAME = "problem5";

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
        new ProbE().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    private static final int MOD = 1_000_000_007;
    private static final int P = 5003;

    class Edge {
        int u;
        int v;
        int c;

        Edge(int u, int v, int c) {
            this.u = u;
            this.v = v;
            this.c = c;
        }
    }

    private void solve() {
        int n = rw.nextInt();
        int m = rw.nextInt();
        int k = rw.nextInt();
        int l = rw.nextInt();

        List<Edge>[] edges;
        boolean[] isTerm = new boolean[n];
        for (int i = 0; i < k; ++i) {
            int x = rw.nextInt() - 1;
            isTerm[x] = true;
        }

        int[][] e = new int[n][26];

        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
            Arrays.fill(e[i], -1);
        }

        for (int i = 0; i < m; ++i) {
            int u = rw.nextInt() - 1;
            int v = rw.nextInt() - 1;
            int c = rw.next().toCharArray()[0] - 'a';
            edges[u].add(new Edge(u, v, c));
            e[u][c] = v;
        }

        Queue<Collection<Integer>> q = new ArrayDeque<>();
        {
            List<Integer> start = new ArrayList<>();
            start.add(0);
            q.add(start);
        }
        HashMap<Long, Integer> hToId = new HashMap<>();
        hToId.put(1L, 0);
        int free = 1;
        List<Integer>[] newEdges = new ArrayList[5_000];
        List<Integer> nTerm = new ArrayList<>();

        while (!q.isEmpty()) {
            Collection<Integer> cur = q.poll();
            long h = getHash(cur);
            int id = hToId.get(h);
            newEdges[id] = new ArrayList<>();
            for (int u : cur) {
                if (isTerm[u]) {
                    nTerm.add(id);
                    break;
                }
            }

            for (int i = 0; i < 26; ++i) {
                SortedSet<Integer> nV = new TreeSet<>();

                for (int u : cur) {
                    for (Edge edge : edges[u]) {
                        if (edge.c == i) {
                            nV.add(edge.v);
                        }
                    }
                }

                long nH = getHash(nV);
                int nId;
                if (!hToId.containsKey(nH)) {
                    nId = free++;
                    hToId.put(nH, nId);
                    q.add(nV);
                } else {
                    nId = hToId.get(nH);
                }
                newEdges[id].add(nId);
            }
        }

        int[][] d = new int[free][l + 1];
        d[0][0] = 1;
        for (int i = 0; i < l; ++i) {
            for (int u = 0; u < free; ++u) {
                if (d[u][i] > 0) {
                    for (int v : newEdges[u]) {
                        d[v][i + 1] = (d[v][i + 1] + d[u][i]) % MOD;
                    }
                }
            }
        }
        int ans = 0;
        for (int i : nTerm) {
            ans = (ans + d[i][l]) % MOD;
        }
        rw.println(ans);
    }

    long getHash(Collection<Integer> list) {
        long h = 0;
        for (int i : list) {
            h = h * P + (i + 1);
        }
        return h;
    }
}

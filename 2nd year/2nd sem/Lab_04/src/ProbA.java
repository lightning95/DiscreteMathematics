import java.io.*;
import java.util.*;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 5/24/15.
 */
public class ProbA {
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
        new ProbA().run();
    }

    private void run() {
        rw = new RW("rainbow.in", "rainbow.out");
        solve();
        rw.close();
    }

    class Edge {
        int u;
        int v;
        int c;
        int id;

        Edge(int u, int v, int c, int id) {
            this.u = u;
            this.v = v;
            this.c = c;
            this.id = id;
        }
    }

    class DSU {
        int[] root;

        DSU(int n) {
            root = new int[n];
            for (int i = 0; i < n; ++i) {
                root[i] = i;
            }
        }

        int getRoot(int u) {
            return root[u] != u ? root[u] = getRoot(root[u]) : root[u];
        }

        boolean canUnion(int u, int v) {
            return getRoot(u) != getRoot(v);
        }

        boolean tryUnion(int u, int v) {
            int rU = getRoot(u);
            int rV = getRoot(v);
            if (rU != rV) {
                root[rU] = rV;
                return true;
            }
            return false;
        }
    }

    private void solve() {
        int n = rw.nextInt();
        int m = rw.nextInt();

        Edge[] allEdges = new Edge[m];

        for (int i = 0; i < m; ++i) {
            int u = rw.nextInt() - 1;
            int v = rw.nextInt() - 1;
            int c = rw.nextInt() - 1;
            allEdges[i] = new Edge(u, v, c, i);
        }

        DSU dsu = new DSU(n);
        boolean[] used = new boolean[100];
        List<Edge> ind = new ArrayList<>();
        boolean[] in = new boolean[m];

        for (Edge e : allEdges) {
            if (!used[e.c] && dsu.tryUnion(e.u, e.v)) {
                used[e.c] = true;
                ind.add(e);
                in[e.id] = true;
            }
        }

        main:
        for (; ; ) {
            // graph of replacements
            List<Edge>[] rEdges = new ArrayList[m];
            for (int i = 0; i < m; ++i) {
                rEdges[i] = new ArrayList<>();
            }

            for (int i = 0; i < ind.size(); i++) {
                Edge edge = ind.get(i); // I
                in[edge.id] = false;

                DSU curDsu = new DSU(n);
                for (int j = 0; j < ind.size(); ++j) {
                    if (i != j) {
                        curDsu.tryUnion(ind.get(j).u, ind.get(j).v);
                    }
                }

                for (Edge e : allEdges) {
                    if (curDsu.canUnion(e.u, e.v)) {
                        rEdges[edge.id].add(new Edge(edge.id, e.id, 0, 0));
                    }
                    used[edge.c] = false;
                    if (!used[e.c]) {
                        rEdges[e.id].add(new Edge(e.id, edge.id, 0, 0));
                    }
                    used[edge.c] = true;
                }
                in[edge.id] = true;
            }

            // x1 to queue
            Queue<Integer> q = new ArrayDeque<>();
            int[] d = new int[m];
            Arrays.fill(d, Integer.MAX_VALUE);
            for (Edge e : allEdges) {
                if (!in[e.id] && dsu.canUnion(e.u, e.v)) {
                    q.add(e.id);
                    d[e.id] = 0;
                }
            }

            int[] prev = new int[m];
            Arrays.fill(prev, -1);
            for (; !q.isEmpty(); ) {
                int u = q.poll();
                for (Edge e : rEdges[u]) {
                    if (!in[e.v] && !used[allEdges[e.v].c]) {
                        // shortest path to X2 is found
                        List<Edge> newC = new ArrayList<>();
                        for (int cur = e.v; cur != -1; cur = prev[cur]) {
                            if (!in[cur]) {
                                newC.add(allEdges[cur]);
                                in[cur] = true;
                            } else if (in[cur]) {
                                in[cur] = false;
                            }
                        }
                        ind.stream().filter(edge -> !in[edge.id]).forEach(edge -> {
                            newC.add(edge);
                            in[edge.id] = true;
                        });
                        ind = newC;

                        continue main;
                    }
                    if (d[u] + 1 < d[e.v]) {
                        d[e.v] = d[u] + 1;
                        prev[e.v] = u;
                        q.add(e.v);
                    }
                }
            }

            // shortest path P isn't found
            break;
        }

        rw.println(ind.size());
        ind.stream().forEach(e -> rw.print(e.id + 1 + " "));
        rw.println();
    }
}
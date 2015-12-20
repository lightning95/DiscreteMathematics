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
            refresh();
        }

        void refresh() {
            for (int i = 0; i < root.length; ++i) {
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
        {
            final int[] id = {0};
            Arrays.setAll(allEdges, edge -> new Edge(rw.nextInt() - 1, rw.nextInt() - 1, rw.nextInt() - 1, id[0]++));
        }

        DSU dsu = new DSU(n);
        boolean[] usedColor = new boolean[100];
        List<Edge> curInd = new ArrayList<>();
        boolean[] inInd = new boolean[m];

        for (Edge e : allEdges) {
            if (!usedColor[e.c] && dsu.tryUnion(e.u, e.v)) {
                usedColor[e.c] = true;
                curInd.add(e);
                inInd[e.id] = true;
            }
        }
        List<Edge>[] rEdges = new ArrayList[m];
        Arrays.setAll(rEdges, i -> new ArrayList());

        DSU curDsu = new DSU(n);
        int[] d = new int[m];
        int[] prev = new int[m];
        Queue<Integer> q = new ArrayDeque<>();
        List<Edge> newInd;
        for (; ; ) {
            // graph of replacements
            Arrays.stream(rEdges).forEach(List::clear);
            for (int i = 0; i < curInd.size(); i++) {
                Edge edge = curInd.get(i); // I
                inInd[edge.id] = false;
                usedColor[edge.c] = false;

                curDsu.refresh();
                for (int j = 0; j < curInd.size(); ++j) {
                    if (i != j) {
                        curDsu.tryUnion(curInd.get(j).u, curInd.get(j).v);
                    }
                }

                for (Edge e : allEdges) {
                    if (inInd[e.id] || e.id == edge.id) {
                        continue;
                    }
                    if (curDsu.canUnion(e.u, e.v)) { // ->
                        rEdges[edge.id].add(new Edge(edge.id, e.id, 0, 0));
                    }
                    if (!usedColor[e.c]) { // <-
                        rEdges[e.id].add(new Edge(e.id, edge.id, 0, 0));
                    }
                }
                inInd[edge.id] = true;
                usedColor[edge.c] = true;
            }

            // x1 to queue
            q.clear();
            Arrays.fill(d, Integer.MAX_VALUE);
            for (Edge e : allEdges) {
                if (!inInd[e.id] && dsu.canUnion(e.u, e.v)) {
                    q.add(e.id);
                    d[e.id] = 0;
                }
            }

            Arrays.fill(prev, -1);
            for (; !q.isEmpty(); ) {
                int u = q.poll();
                rEdges[u].stream().filter(e -> d[u] + 1 < d[e.v]).forEach(e -> {
                    d[e.v] = d[u] + 1;
                    prev[e.v] = u;
                    q.add(e.v);
                });
            }

            int id = -1;
            for (int i = 0; i < m; ++i) {
                if (d[i] < Integer.MAX_VALUE && !inInd[i] && !usedColor[allEdges[i].c]
                        && (id == -1 || d[id] > d[i])) {
                    id = i;
                }
            }
            // shortest path isn't found
            if (id == -1) {
                break;
            }
            // shortest path to X2 is found
            newInd = new ArrayList<>();
            for (int cur = id; cur != -1; cur = prev[cur]) {
                if (!inInd[cur]) {
                    newInd.add(allEdges[cur]);
                    inInd[cur] = true;
                } else if (inInd[cur]) {
                    inInd[cur] = false;
                }
            }
            curInd.stream().filter(edge -> inInd[edge.id]).forEach(newInd::add);
            curInd = newInd;
            curDsu.refresh();
            curInd.forEach(e -> dsu.tryUnion(e.u, e.v));
        }

        rw.println(curInd.size());
        curInd.stream().forEach(e -> rw.print(e.id + 1 + " "));
        rw.println();
    }
}
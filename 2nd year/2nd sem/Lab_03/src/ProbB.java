import java.io.*;
import java.util.*;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 4/29/15.
 */
public class ProbB {
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

        long nextLong() {
            return Long.parseLong(next());
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
        rw = new RW("destroy.in", "destroy.out");
        solve();
        rw.close();
    }

    class Edge {
        int u;
        int v;
        long c;
        int id;

        Edge(int u, int v, long c, int id) {
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

        void union(int u, int v) {
            root[get(u)] = get(v);
        }

        int get(int u) {
            return root[u] != u ? root[u] = get(root[u]) : u;
        }

        boolean dif(int u, int v) {
            return get(u) != get(v);
        }
    }

    private void solve() {
        int n = rw.nextInt();
        int m = rw.nextInt();
        long s = rw.nextLong();
        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; ++i) {
            edges[i] = new Edge(rw.nextInt() - 1, rw.nextInt() - 1, rw.nextLong(), i);
        }
        Arrays.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return Long.compare(o2.c, o1.c);
            }
        });

        DSU dsu = new DSU(n);
        boolean[] us = new boolean[m];
        for (Edge e : edges) {
            if (dsu.dif(e.u, e.v)) {
                dsu.union(e.u, e.v);
                us[e.id] = true;
            }
        }
        long ans = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = m - 1; i >= 0; --i) {
            if (!us[edges[i].id] && ans + edges[i].c <= s) {
                ans += edges[i].c;
                list.add(edges[i].id);
            }
        }

        rw.println(list.size());
        list.stream().sorted().forEach(id -> rw.print(id + 1 + " "));
        rw.println();
    }
}

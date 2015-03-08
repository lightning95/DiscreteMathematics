import java.io.*;
import java.util.*;

/**
 * Created by lightning95 on 10/17/14.
 */

public class ProbD {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbD().run();
    }

    public String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return "-1";
            }
        }
        return st.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "pathbgep";
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + ".out");
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    class Edge {
        int u;
        int v;
        int w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();
        ArrayList<Edge>[] edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            int w = nextInt();
            edges[u].add(new Edge(u, v, w));
            edges[v].add(new Edge(v, u, w));
        }

        final int[] d = new int[n];
        Arrays.fill(d, 1_000_000_000);
        d[0] = 0;

        NavigableSet<Integer> set = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                if (d[a]!= d[b]) {
                    return Integer.compare(d[a], d[b]);
                } else {
                    return Integer.compare(a, b);
                }
            }
        });
        for (int i = 0; i < n; ++i) {
            set.add(i);
        }

        while (!set.isEmpty()) {
            int u = set.pollFirst();
            for (Edge e : edges[u]) {
                int v = e.v;
                int w = e.w;
                if (d[u] + w < d[v]) {
                    set.remove(v);
                    d[v] = d[u] + w;
                    set.add(v);
                }
            }
        }

        for (int dist : d) {
            out.print(dist + " ");
        }
        out.println();
    }
}
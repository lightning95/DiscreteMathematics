import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 10/17/14.
 */

public class ProbF {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbF().run();
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
        String name = "negcycle";
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

    // u -> v
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

        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                int w = nextInt();
                if (w != 1_000_000_000) {
                    edges.add(new Edge(i, j, w));
                }
            }
        }

        int[] d = new int[n];
        int[] prev = new int[n];

        int last = -1;
        for (int k = 0; k < n; ++k) {
            for (Edge e : edges) {
                if (d[e.u] + e.w < d[e.v]) {
                    d[e.v] = d[e.u] + e.w;
                    prev[e.v] = e.u;
                    if (k + 1 == n) {
                        last = e.v;
                    }
                }
            }
        }

        out.println(last > -1 ? "YES" : "NO");

        if (last > -1) {
            for (int i = 0; i < n; ++i) {
                last = prev[last];
            }
            ArrayList<Integer> ans = new ArrayList<>();
            ans.add(last);
            for (int i = prev[last]; i != last; i = prev[i]) {
                ans.add(i);
            }
            ans.add(last);

            Collections.reverse(ans);
            out.println(ans.size());
            for (int i : ans) {
                out.print((i + 1) + " ");
            }
            out.println();
        }
    }
}
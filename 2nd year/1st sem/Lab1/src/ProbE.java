import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 9/26/14.
 */

public class ProbE {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbE().run();
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
        String name = "cycle";
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

    int[] color;
    ArrayList<Integer>[] edges;
    ArrayList<Integer> ans;
    boolean cycle;
    int[] prev;

    void rec(int u) {
        color[u] = 1;

        for (int v : edges[u]) {
            if (cycle) {
                break;
            }
            if (color[v] == 0) {
                prev[v] = u;
                rec(v);
            } else if (color[v] == 1) {
                cycle = true;
                ans.add(v);
                for (int cur = u; cur != v; cur = prev[cur]) {
                    ans.add(cur);
                }
                return;
            }
        }

        color[u] = 2;
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();

        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            edges[u].add(v);
        }

        color = new int[n];
        prev = new int[n];
        ans = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            if (color[i] == 0) {
                prev[i] = -1;
                rec(i);
            }
        }

        Collections.reverse(ans);
        out.print(cycle ? "YES\n" : "NO");
        for (int i : ans) {
            out.print((i + 1) + " ");
        }
        out.println();
    }
}

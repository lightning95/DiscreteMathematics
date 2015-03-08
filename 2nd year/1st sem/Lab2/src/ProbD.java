import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 10/5/14.
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
        String name = "bipartite";
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

    ArrayList<Integer>[] edges;
    int[] color;

    boolean ok = true;

    void dfs(int u, int c) {
        color[u] = c;

        for (int v : edges[u]) {
            if (color[v] == -1) {
                dfs(v, 1 - c);
            } else if (color[v] == c) {
               ok = false;
            }
        }
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
            edges[v].add(u);
        }

        color = new int[n];
        Arrays.fill(color, -1);

        for (int i = 0; i < n; ++i) {
            if (color[i] == -1) {
                dfs(i, 0);
            }
        }

        out.println(ok ? "YES" : "NO");
    }
}
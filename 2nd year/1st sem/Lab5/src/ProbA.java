import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbA {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbA().run();
    }

    String next() {
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

    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    int nextInt() {
        return Integer.parseInt(next());
    }

    void run() throws IOException {
        String name = "matching";
        InputStream input = System.in;
        OutputStream output = System.out;
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new FileOutputStream(name + ".out");
            }
        } catch (Exception e) {
        }

        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);

        solve();

        br.close();
        out.close();
    }

    boolean[] vis;
    boolean[][] a;
    int[] pair;

    boolean dfs(int u) {
        vis[u] = true;

        for (int v = 0; v < a[u].length; ++v) {
            if (a[u][v] && (pair[v] == -1 || !vis[pair[v]] && dfs(pair[v]))) {
                pair[v] = u;
                return true;
            }
        }

        return false;
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();
        int k = nextInt();

        a = new boolean[n][m];
        for (int i = 0; i < k; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            a[u][v] = true;
        }

        pair = new int[m];
        vis = new boolean[n];
        Arrays.fill(pair, -1);
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            Arrays.fill(vis, false);
            if (dfs(i)) {
                ++ans;
            }
        }

        out.println(ans);
    }
}

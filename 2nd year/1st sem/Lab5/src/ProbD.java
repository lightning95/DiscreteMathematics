import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbD {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbD().run();
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
        String name = "paths";
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
    ArrayList<Integer>[] edges;
    int[] pair;

    boolean dfs(int u) {
        vis[u] = true;

        for (int v : edges[u]) {
            if (pair[v] == -1 || !vis[pair[v]] && dfs(pair[v])) {
                pair[v] = u;
                return true;
            }
        }

        return false;
    }

    boolean count(int u){
        vis[u] = true;

        if (pair[u] == -1){
            return true;
        }

        if (vis[pair[u]]){
            return false;
        } else {
            return count(pair[u]);
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
        }

        pair = new int[n];
        vis = new boolean[n];
        Arrays.fill(pair, -1);
        for (int i = 0; i < n; ++i) {
            Arrays.fill(vis, false);
            dfs(i);
        }

        int ans = 0;
        Arrays.fill(vis, false);
        for (int i = 0; i < n; ++i){
            if (!vis[i] && count(i)){
                ++ans;
            }
        }

        out.println(ans);
    }
}

import java.io.*;
import java.util.ArrayList;
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
        String name = "euler";
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

    void dfs(int u) {
        for (int v = 0; v < a.length; ++v) {
            if (a[u][v] > 0) {
                --a[u][v];
                --a[v][u];
                dfs(v);
            }
        }
        ans.add(u);
    }

    ArrayList<Integer> ans;
    int[][] a;

    void solve() {
        int n = nextInt();
        a = new int[n][n];
        ArrayList<Integer> odd = new ArrayList<Integer>();
        for (int i = 0; i < n; ++i) {
            int m = nextInt();
            if (m % 2 == 1) {
                odd.add(i);
            }
            for (int j = 0; j < m; ++j) {
                int v = nextInt() - 1;
                ++a[i][v];
            }
        }

        ans = new ArrayList<Integer>();
        if (odd.size() > 2) {
            out.println(-1);
            return;
        } else if (!odd.isEmpty()) {
            dfs(odd.get(0));
        } else {
            dfs(0);
        }

        out.println(ans.size() - 1);
        for (int u : ans) {
            out.print((u + 1) + " ");
        }
        out.println();
    }
}

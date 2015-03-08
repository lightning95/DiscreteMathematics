import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 10/17/14.
 */

public class ProbC {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbC().run();
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
        String name = "pathsg";
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
        int[][] a = new int[n][n];
        for (int i = 0; i < n; ++i){
            Arrays.fill(a[i], 1_000_000_000);
            a[i][i] = 0;
        }

        for (int i = 0; i < m; ++i){
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            a[u][v] = nextInt();
        }

        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    a[i][j] = Math.min(a[i][j], a[i][k] + a[k][j]);
                }
            }
        }

        for (int[] b : a) {
            for (int i : b) {
                out.print(i + " ");
            }
            out.println();
        }
    }
}
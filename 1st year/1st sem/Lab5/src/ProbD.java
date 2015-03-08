import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

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

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    String nextLine() throws IOException {
        return br.readLine();
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "knapsack.";
        try {
            File f = new File(name + "in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + "out");
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    void solve() {
        int n = nextInt(), m = nextInt();

        int[] w = new int[n];
        for (int i = 0; i < n; ++i) {
            w[i] = nextInt();
        }
        int[] c = new int[n];
        for (int i = 0; i < n; ++i) {
            c[i] = nextInt();
        }
        int[][] d = new int[n + 1][m + 1];
        int ans = 0;
        Pair id = new Pair(0, 0);

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= m; ++j) {
                d[i + 1][j] = Math.max(d[i][j], d[i + 1][j]);
                if (j + w[i] <= m) {
                    d[i + 1][j + w[i]] = Math.max(d[i][j] + c[i], d[i + 1][j + w[i]]);
                }
                if (d[i + 1][j] > ans) {
                    ans = d[i + 1][j];
                    id = new Pair(i + 1, j);
                }
            }
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int x = id.x, y = id.y; x > 0; --x) {
            if (d[x][y] == d[x - 1][y]) {
                continue;
            }
            list.add(x);
            y -= w[x - 1];
        }
        Collections.reverse(list);
        out.println(list.size());
        for (int i : list) {
            out.print(i + " ");
        }
        out.println();
    }

    class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
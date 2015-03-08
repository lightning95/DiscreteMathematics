import java.io.*;
import java.util.*;

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
        String name = "lcs.";
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
        int n = nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = nextInt();
        }
        int m = nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; ++i) {
            b[i] = nextInt();
        }
        int[][] d = new int[n + 1][m + 1];
        Pair[][] prev = new Pair[n + 1][m + 1];
        for (int i = 0; i <= n; ++i) {
            Arrays.fill(prev[i], new Pair(-1, -1));
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                if (a[i - 1] == b[j - 1]) {
                    d[i][j] = d[i - 1][j - 1] + 1;
                    prev[i][j] = new Pair(i - 1, j - 1);
                } else if (d[i - 1][j] > d[i][j - 1]) {
                    d[i][j] = d[i - 1][j];
                    prev[i][j] = new Pair(i - 1, j);
                } else {
                    d[i][j] = d[i][j - 1];
                    prev[i][j] = new Pair(i, j - 1);
                }
            }
        }
        List<Integer> ans = new ArrayList<Integer>();

        for (Pair cur = new Pair(n, m), pr; cur.x != 0 && cur.y != 0; cur = pr) {
            pr = prev[cur.x][cur.y];
            if (cur.x - pr.x + cur.y - pr.y > 1) {
                ans.add(a[cur.x - 1]);
            }
        }
        Collections.reverse(ans);
        out.println(ans.size());
        for (int i : ans) {
            out.print(i + " ");
        }
    }

    class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
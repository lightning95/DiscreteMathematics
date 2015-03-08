import java.io.*;
import java.util.StringTokenizer;

public class ProbB {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbB().run();
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
        String name = "dsu";
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + ".out");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    void solve() {
        int n = nextInt();
        rt = new int[n][4];
        for (int i = 0; i < n; ++i) {
            rt[i][0] = i;
            rt[i][1] = i + 1;
            rt[i][2] = i + 1;
            rt[i][3] = 1;
        }
        for (String s = nextToken(); !eof; s = nextToken()) {
            if (s.equals("union")) {
                union(nextInt() - 1, nextInt() - 1);
            } else {
                int x = get(nextInt() - 1);
                out.println(rt[x][1] + " " + rt[x][2] + " " + rt[x][3]);
            }
        }
    }

    int[][] rt;

    int get(int x) {
        if (rt[x][0] != x) {
            rt[x][0] = get(rt[x][0]);
        }
        return rt[x][0];
    }

    void union(int x, int y) {
        x = get(x);
        y = get(y);
        if (x == y) {
            return;
        }
        if (rt[x][3] > rt[y][3]) {
            rt[y][0] = x;
            rt[x][1] = Math.min(rt[x][1], rt[y][1]);
            rt[x][2] = Math.max(rt[x][2], rt[y][2]);
            rt[x][3] += rt[y][3];
        } else {
            rt[x][0] = y;
            rt[y][1] = Math.min(rt[x][1], rt[y][1]);
            rt[y][2] = Math.max(rt[x][2], rt[y][2]);
            rt[y][3] += rt[x][3];
        }
    }
}
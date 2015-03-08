import java.io.*;
import java.util.Arrays;
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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "parking";
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
        rt = new int[n];
        for (int i = 0; i < n; ++i) {
            rt[i] = i;
        }
        boolean[] b = new boolean[n];
        for (int i = 0; i < n; ++i) {
            int id = nextInt() - 1;
            if (!b[id]) {
                b[id] = true;
                if (b[(id - 1 + n) % n]) {
                    union((id - 1 + n) % n, id);
                }
                if (b[(id + 1) % n]) {
                    union(id, (id + 1) % n);
                }
                out.print((id + 1) + " ");
            } else {
                int x = (get(id) + 1) % n;
                b[x] = true;
                union(id, x);
                if (b[(x + 1) % n]) {
                    union(x, (x + 1) % n);
                }
                out.print((x + 1) + " ");
            }
        }
        out.println();
    }

    int[] rt;

    int get(int x) {
        if (rt[x] != x) {
            rt[x] = get(rt[x]);
        }
        return rt[x];
    }

    void union(int x, int y) {
        x = get(x);
        y = get(y);
        rt[x] = y;
    }
}
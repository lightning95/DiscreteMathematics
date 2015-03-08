import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 9/26/14.
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
        String name = "shortpath";
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

    boolean[] was;
    int[] pos;
    ArrayList<Pair>[] edges;
    int[] id;
    int time;

    void rec(int u) {
        was[u]= true;

        for (Pair p : edges[u]) {
            if (!was[p.v]) {
                rec(p.v);
            }
        }

        pos[u] = was.length - ++time;
        id[pos[u]] = u;
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();

        int s = nextInt() - 1;
        int t = nextInt() - 1;

        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            int w = nextInt();
            edges[u].add(new Pair(v, w));
        }

        was = new boolean[n];
        id = new int[n];
        pos = new int[n];
        time = 0;

        for (int i = 0; i < n; ++i) {
            if (!was[i]) {
                rec(i);
            }
        }

        int[] d = new int[n];
        Arrays.fill(d, 1_000_000_000);
        d[pos[s]] = 0;
        for (int i = pos[s]; i < n; ++i) {
            for (Pair p : edges[id[i]]) {
                if (i < pos[p.v]) {
                    d[pos[p.v]] = Math.min(d[pos[p.v]], d[i] + p.w);
                }
            }
        }

        out.println(d[pos[t]] != 1_000_000_000 ? d[pos[t]] : "Unreachable");
    }

    class Pair {
        int v, w;

        Pair(int v, int w) {
            this.v = v;
            this.w = w;

        }
    }
}

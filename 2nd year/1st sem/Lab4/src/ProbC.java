import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;


public class ProbC {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbC().run();
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
        String name = "mindiff";
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

    int[] rt;

    int get(int x) {
        if (x != rt[x]) {
            rt[x] = get(rt[x]);
        }
        return rt[x];
    }

    void union(int x, int y) {
        rt[get(x)] = get(y);
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();
        Edge[] a = new Edge[m];
        for (int i = 0; i < m; ++i) {
            a[i] = new Edge(nextInt() - 1, nextInt() - 1, nextInt());
        }
        Arrays.sort(a, new Comparator<Edge>() {
            @Override
            public int compare(Edge a, Edge b) {
                return Long.compare(a.w, b.w);
            }
        });
        rt = new int[n];

        int ans = Integer.MAX_VALUE;
        for (int start = 0; start < m; ++start) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (int i = 0; i < n; ++i) {
                rt[i] = i;
            }

            int kol = 0;
            for (int i = start; i < m; ++i) {
                int u = a[i].u;
                int v = a[i].v;
                int w = a[i].w;
                if (get(u) != get(v)) {
                    union(u, v);
                    ++kol;
                    min = Math.min(min, w);
                    max = Math.max(max, w);
                }
            }
            if (kol == n - 1) {
                ans = Math.min(ans, max - min);
            }
        }
        out.println(ans < Integer.MAX_VALUE ? "YES\n" + ans : "NO");
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
}

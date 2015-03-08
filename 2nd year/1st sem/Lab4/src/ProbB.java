import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class ProbB {
    boolean eof;

    public static void main(String[] args) throws IOException {
        new ProbB().run();
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
        String name = "spantree2";
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

    int get(int x){
        if (x != rt[x]){
            rt[x] = get(rt[x]);
        }
        return rt[x];
    }

    void union(int x, int y){
        rt[get(x)] = get(y);
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();
        Edge[] a = new Edge[m];
        for (int i = 0; i < m; ++i) {
            a[i] = new Edge(nextInt() - 1, nextInt() - 1, nextInt());
        }
        Arrays.sort(a);

        rt = new int[n];
        for (int i = 0; i < n; ++i) {
            rt[i] = i;
        }
        long ans = 0;
        for (Edge e : a){
            if (get(e.u) != get(e.v)){
                union(e.u, e.v);
                ans += e.w;
            }
        }
        out.println(ans);
    }

    class Edge implements Comparable<Edge> {
        int u;
        int v;
        long w;

        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        public int compareTo(Edge e) {
            return Long.compare(w, e.w);
        }
    }
}
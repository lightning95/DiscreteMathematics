import java.io.*;
import java.util.*;

/**
 * Created by lightning95 on 10/17/14.
 */

public class ProbA {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbA().run();
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
        String name = "pathbge1";
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

    void solve() {
        int n = nextInt();
        int m = nextInt();

        ArrayList<Integer>[] edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            edges[u].add(v);
            edges[v].add(u);
        }

        int[] d = new int[n];
        Arrays.fill(d, 1_000_000_000);
        d[0] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);
        for (; !q.isEmpty(); ) {
            int u = q.poll();
            for (int v : edges[u]) {
                if (d[u] + 1 < d[v]) {
                    d[v] = d[u] + 1;
                    q.add(v);
                }
            }
        }
        for (int dist : d) {
            out.print(dist + " ");
        }
        out.println();
    }
}
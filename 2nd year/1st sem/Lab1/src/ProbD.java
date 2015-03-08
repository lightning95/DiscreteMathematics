import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 9/26/14.
 */

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
        String name = "game";
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

    ArrayList<Integer>[] edges;

    int[] count;

    void rec(int u){
        count[u] = 0;
        for (int v : edges[u]){
            if (count[v] == -1){
                rec(v);
            }
            if (count[v] == 0){
                count[u] = 1;
            }
        }
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();
        int s = nextInt() - 1;

        edges = new ArrayList[n];

        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            edges[u].add(v);
        }

        count = new int[n];
        Arrays.fill(count, -1);
        rec(s);
        out.println((count[s] == 1 ? "First" : "Second") + " player wins");
    }
}

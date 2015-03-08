import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 9/26/14.
 */

public class ProbF {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbF().run();
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
        String name = "hamiltonian";
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
    ArrayList<Integer>[] revEdges;

    boolean[] was;

    int[] id;

    int pos;

    void revRec(int u) {
        was[u] = true;

        for (int v : revEdges[u]) {
            if (!was[v]) {
                revRec(v);
            }
        }

        id[pos++] = u;
    }

    boolean exists(int n) {
        id = new int[n];
        was = new boolean[n];
        for (int i = 0; i < n; ++i) {
            if (!was[i]) {
                revRec(i);
            }
        }
        for (int i = 0; i < n - 1; ++i) {
            if (!edges[id[i]].contains(id[i + 1])) {
                return false;
            }
        }
        return true;
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();

        int[] cnt1 = new int[n];
        int[] cnt2 = new int[n];
        edges = new ArrayList[n];
        revEdges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
            revEdges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            edges[u].add(v);
            revEdges[v].add(u);
            ++cnt1[u];
            ++cnt2[v];
        }

        int cntOut = 0;
        int cntIn = 0;
        for (int i = 0; i < n; ++i) {
            if (cnt1[i] == 0) {
                ++cntOut;
            }
            if (cnt2[i] == 0) {
                ++cntIn;
            }
        }

        out.println((cntOut > 1 || cntIn > 1 || !exists(n)) ? "NO" : "YES");
    }
}

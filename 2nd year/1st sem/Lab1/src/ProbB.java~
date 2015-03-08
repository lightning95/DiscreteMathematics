import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by lightning95 on 9/26/14.
 */

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
        String name = "cond";
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

    int[] color;
    int[] tout;
    ArrayList<Integer>[] edges;
    ArrayList<Integer>[] revEdges;
    int time;
    boolean[] was;

    void revDfs(int u) {
        was[u] = true;

        for (int v : revEdges[u]) {
            if (!was[v]) {
                revDfs(v);
            }
        }

        tout[u] = time++;
    }


    int colorId;
    void dfs(int u){
        color[u] = colorId;

        for (int v : edges[u]) {
            if (color[v] == 0) {
                dfs(v);
            }
        }
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();

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
        }

        was = new boolean[n];
        tout = new int[n];
        for (int i = 0; i < n; ++i) {
            if (!was[i]) {
                revDfs(i);
            }
        }

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < n; ++i) {
            map.put(tout[i], i);
        }

        color = new int[n];
        Arrays.fill(was, false);
        colorId = 1;
        while (!map.isEmpty()){
            int cur = map.lastKey();
            cur = map.remove(cur);
            if (color[cur] == 0){
                dfs(cur);
                ++colorId;
            }
        }

        out.println(colorId - 1);
        for (int i : color){
            out.print((colorId - i) + " ");
        }
        out.println();
    }
}

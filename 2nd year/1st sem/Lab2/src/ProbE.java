import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by lightning95 on 10/5/14.
 */

public class ProbE {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbE().run();
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
        String name = "bicone";
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

    int[] tup;
    int[] tin;
    ArrayList<Integer>[] edges;
    int time;
    Stack<Integer> stack;
    int id;
    int[] color;

    void dfs(int u, int p) {
        tin[u] = ++time;
        tup[u] = time;
        stack.push(u);

        for (int v : edges[u]) {
            if (tin[v] == 0) {
                int size = stack.size();
                dfs(v, u);
                tup[u] = Math.min(tup[u], tup[v]);
                if (tup[v] > tin[u]) {
                    // uv - bridge
                    while(stack.size() > size){
                        int cur = stack.pop();
                        color[cur] = id;
                    }
                    ++id;
                }
            } else if (p != v) {
                tup[u] = Math.min(tup[u], tin[v]);
            }
        }
    }

    void solve() {
        int n = nextInt();
        int m = nextInt();

        edges = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; ++i) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;
            edges[u].add(v);
            edges[v].add(u);
        }

        tup = new int[n];
        tin = new int[n];
        color = new int[n];
        stack = new Stack<>();
        for (int i = 0; i < n; ++i) {
            if (tin[i] == 0) {
                dfs(i, -1);
                while (!stack.isEmpty()){
                    color[stack.pop()] = id;
                }
                ++id;
            }
        }

        out.println(id);
        for (int i : color) {
            out.print((i + 1) + " ");
        }
        out.println();
    }
}
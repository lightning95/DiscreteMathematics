import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    String nextLine() throws IOException {
        return br.readLine();
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "lis.";
        try {
            File f = new File(name + "in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + "out");
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
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = nextInt();
        }
        int[] d = new int[n];
        int[] prev = new int[n];
        int id = 0;
        for (int i = 0; i < n; ++i) {
            d[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < i; ++j) {
                if (a[i] > a[j] && d[i] < d[j] + 1) {
                    d[i] = d[j] + 1;
                    prev[i] = j;
                }
            }
            if (d[id] < d[i]) {
                id = i;
            }
        }
        out.println(d[id]);
        List<Integer> ans = new ArrayList<Integer>();
        for (; id > -1; id = prev[id]) {
            ans.add(a[id]);
        }
        Collections.reverse(ans);
        for (int i : ans) {
            out.print(i + " ");
        }
        out.println();
    }
}
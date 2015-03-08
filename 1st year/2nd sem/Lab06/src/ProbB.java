import javafx.collections.transformation.SortedList;

import java.io.*;
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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "binsearch";
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

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    void solve() {
        int n = nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i){
            a[i] = nextInt();
        }
        int kol = nextInt();
        for (int i = 0; i < kol; ++i){
            int x = nextInt();
            int l = -1;
            int r = n;
            while (l < r - 1) {
                int m = (l + r) >> 1;
                if (a[m] < x) {
                    l = m;
                } else {
                    r = m;
                }
            }
            int ans = (r + 1 <= n && a[r] == x ? (r + 1) : -1);
            out.print(ans + " ");
            l = -1;
            r = n;
            while (l < r - 1) {
                int m = (l + r) >> 1;
                if (a[m] <= x) {
                    l = m;
                } else {
                    r = m;
                }
            }
            out.println(ans == -1 ? -1 : r);
        }
    }
}
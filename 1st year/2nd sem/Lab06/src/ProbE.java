import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "radixsort";
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
        int n = nextInt(), m = nextInt(), k = nextInt();
        char[][] a = new char[n][m];
        for (int i = 0; i < n; ++i) {
            a[i] = nextToken().toCharArray();
        }
        for (int i = 0; i < k; ++i) {
            ArrayList<char[]>[] list = new ArrayList[26];
            for (int j = 0; j < 26; ++j) {
                list[j] = new ArrayList<char[]>();
            }
            for (int j = 0; j < n; ++j) {
                list[a[j][m - i - 1] - 'a'].add(a[j]);
            }
            for (int j = 0, uk = 0; j < 26; ++j) {
                for (char[] t : list[j]) {
                    a[uk++] = t;
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            out.println(new String(a[i]));
        }
    }
}
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */
public class ProblemD {
    RW rw;
    String FILE_NAME = "nfc";

    public static void main(String[] args) {
        new ProblemD().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    private void solve() {
        int n = rw.nextInt();
        int start = rw.next().charAt(0) - 'A';

        List<Integer>[] small = new ArrayList[26];
        List<Pair<Integer, Integer>>[] rules = new ArrayList[26];
        for (int i = 0; i < 26; ++i) {
            small[i] = new ArrayList<>();
            rules[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; ++i) {
            String s = rw.nextLine();
            if (s.length() > 6) {
                rules[s.charAt(0) - 'A'].add(new Pair<>(s.charAt(5) - 'A', s.charAt(6) - 'A'));
            } else {
                small[s.charAt(5) - 'a'].add(s.charAt(0) - 'A');
            }
        }

        char[] w = rw.next().toCharArray();
        int m = w.length;
        long[][][] dp = new long[26][m][m];
        for (int i = 0; i < m; ++i) {
            for (int j : small[w[i] - 'a']) {
                ++dp[j][i][i];
            }
        }
        long mod = 1_000_000_007;
        for (int i = 1; i < m; ++i) {
            for (int j = 0; j + i < m; ++j) {
                for (int k = j + 1; k <= j + i; ++k) {
                    for (int let = 0; let < 26; ++let) {
                        for (Pair<Integer, Integer> p : rules[let]) {
                            dp[let][j][j + i] += dp[p.getKey()][j][k - 1] * dp[p.getValue()][k][j + i];
                            dp[let][j][j + i] %= mod;
                        }
                    }
                }
            }
        }
        rw.println(dp[start][0][m - 1]);
    }

    class RW {
        StringTokenizer st;
        PrintWriter out;
        BufferedReader br;
        private boolean eof;

        RW(String inputFile, String outputFile) {
            br = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(new OutputStreamWriter(System.out));

            File f = new File(inputFile);
            if (f.exists() && f.canRead()) {
                try {
                    br = new BufferedReader(new FileReader(inputFile));
                    out = new PrintWriter(new FileWriter(outputFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String nextLine() {
            String s = "";
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    eof = true;
                    return "-1";
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        void println() {
            out.println();
        }

        void println(Object o) {
            out.println(o);
        }

        void print(Object o) {
            out.print(o);
        }

        void close() {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}

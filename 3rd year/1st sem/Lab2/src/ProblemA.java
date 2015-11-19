import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */

public class ProblemA {
    RW rw;
    String FILE_NAME = "automaton";

    public static void main(String[] args) {
        new ProblemA().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    private void solve() {
        int n = rw.nextInt();
        int start = rw.next().charAt(0) - 'A' + 1;

        List<Pair<Integer, Integer>>[] rules = new ArrayList[27];
        for (int i = 1; i < 27; ++i) {
            rules[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; ++i) {
            char[] s = rw.nextLine().toCharArray();
            rules[s[0] - 'A' + 1].add(new Pair<>(s[5] - 'a', s.length > 6 ? s[6] - 'A' + 1 : 0));
        }

        int m = rw.nextInt();
        for (int i = 0; i < m; ++i) { // 20
            char[] s = rw.next().toCharArray();
            boolean[] dp1 = new boolean[27];
            dp1[start] = true;
            boolean[] dp2 = new boolean[27];
            for (char c : s) { // 10000
                for (int j = 1; j < 27; ++j) { // 26
                    if (dp1[j]) {
                        rules[j].stream().filter(p -> p.getKey() == c - 'a').forEach(p -> dp2[p.getValue()] = true);
                    }
                }
                System.arraycopy(dp2, 0, dp1, 0, 27);
                Arrays.fill(dp2, false);
            }

            rw.println(dp1[0] ? "yes" : "no");
        }
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

import javafx.util.Pair;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 4/29/15.
 */
public class ProbE {
    RW rw;

    class RW {
        private boolean eof;
        StringTokenizer st;
        PrintWriter out;
        BufferedReader br;

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

        long nextLong() {
            return Long.parseLong(next());
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

    public static void main(String[] args) {
        new ProbE().run();
    }

    private void run() {
        rw = new RW("cycles.in", "cycles.out");
        solve();
        rw.close();
    }

    private void solve() {
        int n = rw.nextInt();
        int m = rw.nextInt();
        Pair<Integer, Integer>[] w = new Pair[n];
        for (int i = 0; i < n; ++i) {
            w[i] = new Pair<>(rw.nextInt(), i);
        }
        if (m == 0) {
            final int[] ans = {0};
            Arrays.stream(w).forEach(x -> ans[0] += x.getKey());
            rw.println(ans[0]);
            return;
        }

        int[] cycle = new int[m];
        for (int i = 0; i < m; ++i) {
            int c = rw.nextInt();
            int h = 0;
            for (int j = 0; j < c; ++j) {
                h += (1 << (rw.nextInt() - 1));
            }
            cycle[i] = h;
        }
        Arrays.sort(w, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return Integer.compare(o2.getKey(), o1.getKey());
            }
        });
        int ans = 0;
        cycle:
        for (int i = 0, h = 0; i < n; ++i) {
            int cur = h + (1 << w[i].getValue());
            for (int j = 0; j < m; ++j) {
                if ((cycle[j] & cur) == cycle[j]) {
                    continue cycle;
                }
            }
            ans += w[i].getKey();
            h = cur;
        }
        rw.println(ans);
    }
}
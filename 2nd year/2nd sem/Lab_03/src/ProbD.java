import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 4/29/15.
 */
public class ProbD {
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
        new ProbD().run();
    }

    private void run() {
        rw = new RW("check.in", "check.out");
        solve();
        rw.close();
    }

    boolean equals(List<Integer> a, List<Integer> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < a.size(); ++i) {
            if (!a.get(i).equals(b.get(i))) {
                return false;
            }
        }
        return true;
    }

    List<Integer> dif(List<Integer> big, List<Integer> small) {
        List<Integer> res = new ArrayList<>();
        for (int i : big) {
            if (!small.contains(i)) {
                res.add(i);
            }
        }
        return res;
    }

    int cH(List<Integer> list) {
        int res = 0;
        for (int i : list) {
            res += 1 << i;
        }
        return res;
    }

    private void solve() {
        int n = rw.nextInt();
        int m = rw.nextInt();
        List<Integer>[] set = new ArrayList[m];

        boolean ch1 = false;

        boolean[] has = new boolean[5000];

        int[] bits = new int[m];
        for (int i = 0; i < m; ++i) {
            int c = rw.nextInt();
            if (c == 0) {
                ch1 = true;
            }
            set[i] = new ArrayList<>();
            for (int j = 0; j < c; ++j) {
                set[i].add(rw.nextInt() - 1);
            }
            int h = cH(set[i]);
            bits[i] = h;
            has[h] = true;
        }

        if (!ch1) {
            rw.println("NO");
            return;
        }
        for (int i = 0; i < m; ++i) {
            for (int mask = 1; mask < (1 << set[i].size()); ++mask) {
                int cur = 0;
                for (int j = 0; j < set[i].size(); ++j) {
                    if ((mask & (1 << j)) > 0) {
                        cur += 1 << set[i].get(j);
                    }
                }

                if (!has[cur]) {
                    rw.println("NO");
                    return;
                }
            }
        }

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < m; ++j) {
                if (i != j && set[i].size() > set[j].size()) {
                    int difh = cH(dif(set[i], set[j]));
                    boolean f = false;
                    for (int k = 0; k < n; ++k) {
                        if (((1 << k) & difh) > 0 && has[bits[j] + (1 << k)]) {
                            f = true;
                        }
                    }
                    if (!f) {
                        rw.println("NO");
                        return;
                    }
                }
            }
        }
        rw.println("YES");
    }
}
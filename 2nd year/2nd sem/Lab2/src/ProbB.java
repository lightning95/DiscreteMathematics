import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class ProbB {
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
        new ProbB().run();
    }

    private void run() {
        rw = new RW("array.in", "array.out");
        solve();
        rw.close();
    }

    class SuffixArray {
        int n;
        char[] cs;
        int[] si;
        int[] is;

        SuffixArray(char[] t) {
            n = t.length;
            cs = new char[n + 1];
            System.arraycopy(t, 0, cs, 0, n);
            cs[n] = 0;

            is = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                is[i] = cs[i];
            }

            Integer[] mas = new Integer[n + 1];
            final int[] p = {0};
            Arrays.stream(mas).forEach(x -> mas[p[0]] = p[0]++);
            Arrays.sort(mas, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return Integer.compare(is[o1], is[o2]);
                }
            });

            p[0] = 0;
            si = new int[n + 1];
            Arrays.stream(mas).forEach(x -> si[p[0]++] = x);

            int[] a = new int[n + 1];
            int[] b = new int[n + 1];

            for (int h = 0; ; ) {
                for (int i = 0; i < n; ++i) {
                    int x = si[i + 1];
                    int y = si[i];
                    b[i + 1] = b[i] + (is[x] > is[y] || is[x + h] > is[y + h] ? 1 : 0);
                }
                for (int i = 0; i <= n; ++i) {
                    is[si[i]] = b[i];
                }
                if (b[n] == n) {
                    break;
                }
                h = Math.max(1, h << 1);
                for (int k = h; k >= 0; k -= h) {
                    Arrays.fill(b, 0);
                    b[0] = k;
                    for (int i = k; i <= n; ++i) {
                        ++b[is[i]];
                    }
                    for (int i = 0; i < n; ++i) {
                        b[i + 1] += b[i];
                    }
                    for (int i = n; i >= 0; --i) {
                        a[--b[si[i] + k > n ? 0 : is[si[i] + k]]] = si[i];
                    }
                    int[] tmp = si;
                    si = a;
                    a = tmp;
                }
            }
        }
    }


    private void solve() {
        SuffixArray suffixArray = new SuffixArray(rw.next().toCharArray());
        Arrays.stream(suffixArray.si).forEach(x -> rw.print(x != suffixArray.n ? x + 1 + " " : ""));
        rw.println();
    }
}

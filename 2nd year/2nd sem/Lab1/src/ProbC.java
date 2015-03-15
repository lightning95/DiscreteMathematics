import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class ProbC {
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
        new ProbC().run();
    }

    private void run() {
        rw = new RW("prefix.in", "prefix.out");
        solve();
        rw.close();
    }

    int[] prefixFun(String s) {
        int[] a = new int[s.length()];
        for (int i = 1; i < a.length; ++i) {
            int j = a[i - 1];
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = a[j - 1];
            }
            j += s.charAt(i) == s.charAt(j) ? 1 : 0;
            a[i] = j;
        }
        return a;
    }

    private void solve() {
        Arrays.stream(prefixFun(rw.next())).forEach(x -> rw.print(x + " "));
        rw.println();
    }
}

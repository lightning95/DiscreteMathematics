import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

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
        rw = new RW("z.in", "z.out");
        solve();
        rw.close();
    }

    int[] zFun(String s) {
        int[] z = new int[s.length()];
        for (int i = 1, l = 0, r = 0; i < z.length; ++i) {
            z[i] = i <= r ? Math.min(z[i - l], r - i + 1) : 0;
            for (; i + z[i] < z.length && s.charAt(z[i]) == s.charAt(i + z[i]); ++z[i]) {
            }
            if (i + z[i] > r) {
                r = i + z[i] - 1;
                l = i;
            }
        }
        return z;
    }

    private void solve() {
        Arrays.stream(zFun(rw.next())).skip(1).forEach(x -> rw.print(x + " "));
        rw.println();
    }
}

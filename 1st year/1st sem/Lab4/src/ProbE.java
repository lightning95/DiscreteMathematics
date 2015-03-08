
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbE {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbE().run();
    }

    String nextToken() {
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

    int nextInt() {
        return Integer.parseInt(nextToken());
    }

    long nextLong() {
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
        String name = "nextbrackets";
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + ".out");
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    void solve() throws IOException {
        String s = nextToken();
        int n = s.length();
        int[] bal = new int[n];
        bal[0] =1;
        for (int i = 1; i < n; ++i){
            if (s.charAt(i) == '(') {
                bal[i] = bal[i - 1] + 1;
            } else {
                bal[i] = bal[i - 1] - 1;
            }
        }
        for (int i = n -1, op = 0, cl = 0; i > 0; --i){
            if (s.charAt(i) == '('){
                ++op;
            } else {
                ++cl;
            }
            if (bal[i - 1] > 0 && s.charAt(i) == '('){
                out.print(s.substring(0, i) + ")");
                for (int j = 0; j < op; ++j ){
                    out.print('(');
                }
                for (int j = 1; j < cl; ++j){
                    out.print(')');
                }
                out.println();
                return;
            }
        }
        out.println("-");
    }
}
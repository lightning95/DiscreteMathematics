import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

public class ProbH {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbH().run();
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

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() {
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
        try {
            File f = new File("brackets2num2.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("brackets2num2.out");
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    void solve() {
        String s = nextToken();
        int n = s.length() / 2;
        long[][] c = new long[(n << 1) + 1][n + 1];
        c[0][0] = 1;
        for (int i = 0; i < n << 1; ++i) {
            for (int j = 0; j <= n; ++j) {
                if (j > 0) {
                    c[i + 1][j - 1] += c[i][j];
                }
                if (j < n) {
                    c[i + 1][j + 1] += c[i][j];
                }
            }
        }

        long[] p = new long[n + 1];
        p[0] = 1;
        for (int i = 1; i <= n; ++i) {
            p[i] = p[i - 1] * 2;
        }

        Stack<Integer> stack = new Stack<Integer>();
        long ans = 0;
        for (int i = 1, bal = 0; i <= n * 2; ++i) {
            if (s.charAt(i - 1) == '(') {
                ++bal;
                stack.add(0);
                continue;
            }
            if (bal < n) {
                ans += c[n * 2 - i][bal + 1] * p[(n * 2 - i - bal) / 2];
            }
            if (bal > 0 && stack.peek() == 0 && s.charAt(i - 1) == ')') {
                --bal;
                stack.pop();
                continue;
            }
            if (bal > 0 && stack.peek() == 0) {
                ans += c[n * 2 - i][bal - 1] * p[(n * 2 - i - bal + 1) / 2];
            }

            if (bal < n && s.charAt(i - 1) == '[') {
                ++bal;
                stack.add(1);
            } else {
                if (bal < n && stack.peek() == 1) {
                    ans += c[n * 2 - i][bal + 1] * p[(n * 2 - i - bal) / 2];
                }
                --bal;
                stack.pop();
            }
        }
        out.println(ans);
    }
}
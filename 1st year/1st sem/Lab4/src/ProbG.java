
import java.io.*;
import java.util.StringTokenizer;

public class ProbG {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbG().run();
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
        String name = "nextpartition";
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
        int x = s.lastIndexOf("+");
        if (x < 0) {
            out.println("No solution");
            return;
        }
        int b = Integer.parseInt(s.substring(x + 1));
        s = s.substring(0, x);
        x = s.lastIndexOf('+');
        if (x < 0) {
            x = s.indexOf('=');
        }

        int a = Integer.parseInt(s.substring(x + 1));

        out.print(s.substring(0, x + 1));
        if (a + 1 <= b - 1) {
            out.print(++a + "+");
            --b;
            while (b - a - a >= 0) {
                out.print(a + "+");
                b -= a;
            }
            out.println(b);
        } else {
            out.println(a + b);
        }
    }
}
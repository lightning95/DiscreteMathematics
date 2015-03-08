
import java.io.*;
import java.util.StringTokenizer;

public class ProbF {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbF().run();
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
        String name = "nextmultiperm";
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
        int n = nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = nextInt() - 1;
        }
        int[] us = new int[n];
        for (int i = n - 1; i > 0; --i) {
            ++us[a[i]];
            if (a[i] > a[i - 1]) {
                ++us[a[i - 1]];
                for (int j = a[i - 1] + 1; j < n; ++j) {
                    if (us[j] > 0) {
                        a[i - 1] = j;
                        --us[j];
                        break;
                    }
                }
                for (int j = 0; j < i; ++j) {
                    out.print(a[j] + 1 + " ");
                }
                for (int j = 0; j < n; ++j) {
                    for (; us[j] > 0; --us[j]) {
                        out.print(j + 1 + " ");
                    }
                }
                out.println();
                return;
            }
        }

        for (int i = 0; i < n; ++i) {
            out.print("0 ");
        }
        out.println();
    }
}
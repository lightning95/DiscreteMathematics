
import java.io.*;
import java.util.StringTokenizer;

public class ProbB {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbB().run();
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
        String name = "nextperm";
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

    void solve() {
        int n = nextInt();
        int[] b = new int[n];
        for (int i = 0; i < n; ++i) {
            b[i] = nextInt() - 1;
        }
        boolean[] us = new boolean[n];
        int[] a = b.clone();
        boolean bad = true;
        for (int i = n - 1; i > 0; --i) {
            us[a[i]] = true;
            if (a[i - 1] > a[i]) {
                us[a[i - 1]] = true;
                for (int j = a[i - 1] - 1; j >= 0; --j) {
                    if (us[j]) {
                        a[i - 1] = j;
                        bad = us[j] = false;
                        break;
                    }
                }
                for (int j = n - 1; j >= 0 && i < n; --j) {
                    if (us[j]) {
                        a[i++] = j;
                    }
                }
                break;
            }
        }

        if (bad) {
            for (int i = 0; i < n; ++i) {
                out.print("0 ");
            }
        } else {
            for (int i = 0; i < n; ++i) {
                out.print(a[i] + 1 + " ");
            }
        }
        out.println();

        us = new boolean[n];
        a = b.clone();
        bad = true;
        for (int i = n - 1; i > 0; --i) {
            us[a[i]] = true;
            if (a[i - 1] < a[i]) {
                us[a[i - 1]] = true;
                for (int j = a[i - 1] + 1; j < n; ++j) {
                    if (us[j]) {
                        a[i - 1] = j;
                        bad = us[j] = false;
                        break;
                    }
                }
                for (int j = 0; j < n && i < n; ++j) {
                    if (us[j]) {
                        a[i++] = j;
                    }
                }
                break;
            }
        }

        if (bad) {
            for (int i = 0; i < n; ++i) {
                out.print("0 ");
            }
        } else {
            for (int i = 0; i < n; ++i) {
                out.print(a[i] + 1 + " ");
            }
        }
        out.println();
    }
}
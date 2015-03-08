
import java.io.*;
import java.util.StringTokenizer;

public class ProbC {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbC().run();
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
        String name = "nextchoose";
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
        int n = nextInt(), k = nextInt();
        int[] a = new int[k];
        for (int i = 0; i < k; ++i){
            a[i] = nextInt() - 1;
        }
        for (int i = k - 1; i >= 0; --i){
            if (n - a[i] - 1 >= k - i){
                for (int j = 0; j < i; ++j){
                    out.print(a[j] + 1 + " ");
                }
                for (int j = i; j < k; ++j){
                    out.print(a[i] + 2 + j - i + " ");
                }
                out.println();
                return;
            }
        }

        out.println(-1);
    }
}
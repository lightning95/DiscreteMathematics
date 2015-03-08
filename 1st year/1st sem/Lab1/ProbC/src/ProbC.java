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
        try {
            File f = new File("mtf.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("mtf.out");
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
        int[] l = new int[26];
        for (int i = 0; i < 26; ++i) {
            l[i] = i;
        }
        for (int i = 0; i < s.length(); ++i) {
            for (int j = 0; j < 26; ++j) {
                if (s.charAt(i) == 'a' + l[j]) {
                    out.print(j + 1 + " ");
                    int x = l[j];
                    for (int k = j; k > 0; --k) {
                        l[k] = l[k - 1];
                    }
                    l[0] = x;
                    break;
                }
            }
        }
    }

}

import java.io.*;
import java.util.StringTokenizer;

public class ProbA {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbA().run();
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
            File f = new File("nextvector.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("nextvector.out");
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
        char[] b = nextToken().toCharArray();
        int n  = b.length;
        char[] a = b.clone();
        for (int i = n - 1; i >= 0; --i){
            if (a[i] == '1'){
                a[i] = '0';
                for (int j = i + 1; j < n; ++j){
                    a[j] = '1';
                }
                break;
            }
        }
        boolean bad = true;
        for (int i = 0; i < n; ++i){
            if (a[i] != b[i]){
                bad = false;
                break;
            }
        }
        out.println(bad ? "-" : new String(a));

        a = b.clone();
        for (int i = n - 1; i >= 0; --i){
            if (a[i] == '0'){
                a[i] = '1';
                for (int j = i + 1; j < n; ++j){
                    a[j] = '0';
                }
                break;
            }
        }
        bad = true;
        for (int i = 0; i < n; ++i){
            if (a[i] != b[i]){
                bad = false;
                break;
            }
        }
        out.println(bad ? "-" : new String(a));
    }
}
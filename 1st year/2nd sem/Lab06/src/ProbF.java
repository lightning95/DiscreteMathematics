import java.io.*;
import java.util.StringTokenizer;

public class ProbF {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbF().run();
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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "antiqs";
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + ".out");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    void solve() {
        int n = nextInt();
        int[] a = new int[n];
        a[0] = 1;
        for (int i = 1; i < n; ++i) {
            a[i] = i + 1;
            int t = a[i / 2];
            a[i / 2] = a[i];
            a[i] = t;
        }
        for (int i = 0; i < n; ++i){
            out.print(a[i] + (i + 1 < n ? " " : "\n"));
        }
    }
}
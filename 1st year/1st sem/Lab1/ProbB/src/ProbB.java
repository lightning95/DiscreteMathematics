import java.io.*;

import java.util.Arrays;

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
        try {
            File f = new File("bwt.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("bwt.out");
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
        String t = nextToken();
        int n = t.length();
        String[] s = new String[n];
        s[0] = t;
        for (int i = 1; i < n; ++i){
            s[i] = t.substring(i) + t.substring(0, i);
        }
        Arrays.sort(s);
        for (int i = 0;i < n; ++i){
            out.print(s[i].charAt(n - 1));
        }
        out.println();
    }

}
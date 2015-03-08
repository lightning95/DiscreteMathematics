import java.io.*;
import java.util.StringTokenizer;

public class ProbG {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbG().run();
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
        String name = "netcheck";
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
        int n = nextInt(), m = nextInt(), k = nextInt();
        int[][] a = new int[m][2];
        for (int i = 0, id = 0; i < k; ++i){
            int t = nextInt();
            for (int j = 0; j < t; ++j){
                int x = nextInt() - 1, y = nextInt() - 1;
                a[id][0] = Math.min(x, y);
                a[id++][1] = Math.max(x, y);
            }
        }
        for (int i = 0; i < 1 << n; ++i){
            String s = Integer.toBinaryString(i);
            while (s.length() < n){
                s = '0' + s;
            }
            char[] b = s.toCharArray();
            for (int j = 0; j < m; ++j){
                if (b[a[j][0]] > b[a[j][1]]){
                    char p = b[a[j][0]];
                    b[a[j][0]] = b[a[j][1]];
                    b[a[j][1]] = p;
                }
            }
            for (int j = 0; j < n - 1; ++j){
                if (b[j] > b[j + 1]){
                    out.println("No");
                    return;
                }
            }
        }
        out.println("Yes");
    }
}
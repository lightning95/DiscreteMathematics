
import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ProbD {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbD().run();
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
            File f = new File("lzw.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("lzw.out");
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
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < 26; ++i) {
            map.put(String.valueOf((char) ('a' + i)), i);
        }
        int l = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (!map.containsKey(s.substring(l, i) + s.charAt(i))) {
                out.print(map.get(s.substring(l, i)) + " ");
                map.put(s.substring(l, i) + s.charAt(i), map.size());
                l = i;
            }
        }
        out.println(map.get(s.substring(l)));
    }

}
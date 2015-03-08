import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProbG {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;
    ArrayList<Integer>[] ans;

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
            File f = new File("part2sets.in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream("part2sets.out");
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    void rec(int n, int k, int last, int setNum) {
        if (last == n) {
            for (ArrayList<Integer> list : ans) {
                for (int a : list) {
                    out.print(a + " ");
                }
                out.println();
            }
            out.println();
            return;
        }
        if (setNum < k){
            ans[setNum].add(last + 1);
            rec(n, k, last + 1, setNum + 1);
            ans[setNum].remove(ans[setNum].size() - 1);
        }
        for (int i = 0; i < setNum; ++i)
        if (n - last - 1 >= k - setNum){
            ans[i].add(last + 1);
            rec(n, k, last + 1, setNum);
            ans[i].remove(ans[i].size() - 1);
        }
    }

    void solve() {
        int n = nextInt(), k = nextInt();
        ans = new ArrayList[k];
        for (int i = 0; i < k; ++i) {
            ans[i] = new ArrayList<Integer>();
        }
        rec(n, k, 0, 0);
    }
}
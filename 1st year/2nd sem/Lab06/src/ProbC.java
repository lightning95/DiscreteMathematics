import java.io.*;
import java.util.Random;
import java.util.StringTokenizer;

public class ProbC {
    boolean eof;
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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "kth";
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

    Random rand = new Random(17);

    int kth(int[] a, int l, int r, int pos) {
        if (l + 1 == r) {
            return a[l];
        }
        int m = l + rand.nextInt(r - l);
        int x = a[m];
        int i = l, j = r - 1;
        while (i <= j) {
            while (a[i] < x) {
                ++i;
            }
            while (a[j] > x) {
                --j;
            }
            if (i <= j) {
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
                ++i;
                --j;
            }
        }
        return pos <= j - l + 1 ? kth(a, l, j + 1, pos) :
                (pos > j - l + 1 && pos < i - l + 1) ? a[j + pos - (j - l + 1)] :
                        kth(a, i, r, pos - (i - l));
    }

    void solve() {
        int n = nextInt(), k = nextInt();
        int[] a = new int[n];
        int x1 = nextInt(), x2 = nextInt(), x3 = nextInt();
        a[0] = nextInt();
        a[1] = nextInt();
        for (int i = 2; i < n; ++i) {
            a[i] = x1 * a[i - 2] + x2 * a[i - 1] + x3;
        }
        /*for (int i = 0; i < n; ++i){
            out.print(a[i] + (i + 1 == n ? "\n" : " "));
        }*/
        out.println(kth(a, 0, n, k));
    }
}
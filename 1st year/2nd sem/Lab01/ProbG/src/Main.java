import java.io.*;
import java.util.StringTokenizer;

public class Main {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new Main().run();
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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "queuemin2";
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

    void solve() {
        int n = nextInt();
        int m = nextInt();
        int k = nextInt();
        int a = nextInt();
        int b = nextInt();
        int c = nextInt();
        int[] x = new int[k];
        for (int i = 0; i < k; ++i) {
            x[i] = nextInt();
        }

        int x1 = 0;
        int x2 = 0;
        int lx1 = 0;
        int lx2 = 0;

        Queue qMin = new Queue(n);
        long ans = 0;

        for (int i = 0; i < n; ++i) {
            int x0 = i < k ? x[i] : a * x1 + b * x2 + c;
            qMin.push(x0);
            if (i >= m - 1) {
                int lx = i - (m - 1) < k ? x[i - (m - 1)] : a * lx1 + b * lx2 + c;
                ans += qMin.peek();
                if (qMin.peek() == lx){
                    qMin.pop();
                }
                lx1 = lx2;
                lx2 = lx;
            }
            x1 = x2;
            x2 = x0;
        }
        out.println(ans);
    }

    class Queue {
        private int[] a;
        private int l, r;

        Queue(int size) {
            a = new int[size];
            l = 0;
            r = 0;
        }

        void push(int x) {
            while (r - 1 >= l && a[r - 1] > x) {
                --r;
            }
            a[r++] = x;
        }

        int pop() {
            return a[l++];
        }

        int peek() {
            return a[l];
        }
    }
}
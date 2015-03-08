import java.io.*;
import java.util.StringTokenizer;

public class ProbH {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbH().run();
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

    int[] copy(int[] x, int l, int r) {
        int[] a = new int[r - l];
        for (int i = l; i < r; ++i) {
            a[i] = x[i];
        }
        return a;
    }

    int[] merge(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        for (int i = 0; i < a.length; ++i){
            res[i] = a[i];
        }
        for (int i = 0; i < b.length; ++i){
            res[i + a.length] = b[i];
        }
        return res;
    }



    int[] bitonicSort(boolean up, int[] x) {
        if (x.length <= 1) {
            return x;
        } else {
            int[] first = bitonicSort(true, copy(x, 0, x.length / 2));
            int[] second = bitonicSort(false, copy(x, x.length / 2 + 1, x.length));
            return bitonicMerge(up, merge(first, second));
        }
    }

    int[] bitonicMerge(boolean up, int[] x) {
        //# assume input x is bitonic, and sorted list is returned
        if (x.length == 1) {
            return x;
        } else {
            bitonicCompare(up, x);
            int[] first = bitonicSort(up, copy(x, 0, x.length / 2));
            int[] second = bitonicSort(up, copy(x, x.length / 2 + 1, x.length));
            return bitonicMerge(up, merge(first, second));
        }
    }
    void bitonicCompare(boolean up, int[] x) {
        int dist = x.length / 2;
        for (int i = 0; i < dist; ++i){
            if (x[i] > x[i + dist] == up){
                int t = x[i];
                x[i] = x[i + dist];
                x[i + dist] = t;
            }
        }
    }

    void solve() {
        int n = nextInt();
        for (int i = 0; i < n; ++i) {

        }
    }
}
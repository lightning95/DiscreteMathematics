import java.io.*;
import java.util.StringTokenizer;

public class ProbA {
    boolean eof;
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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "sort";
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


    class Heap {
        private int[] a;
        private int size;

        Heap(int[] a) {
            this.a = new int[a.length + 1];
            size = 1;
            for (int x : a) {
                add(x);
            }
        }

        private void add(int x) {
            a[size] = x;
            siftUp(size++);
        }

        private void siftUp(int x) {
            for (; x > 1 && a[x] < a[x >> 1]; x >>= 1) {
                int t = a[x];
                a[x] = a[x >> 1];
                a[x >> 1] = t;
            }
        }

        private int pop(){
            int res = a[1];
            a[1] = a[--size]; //size == n s
            siftDown(1);
            return res;
        }

        private void siftDown(int x){
            for (; x << 1  < size; ){
                if (x * 2 + 1 < size && a[x * 2 + 1] < a[x] && a[x * 2 + 1] < a[x * 2]){
                    int t = a[x];
                    a[x] = a[x * 2 + 1];
                    a[x * 2 + 1] = t;
                    x = x * 2 + 1;
                } else if (a[x << 1] < a[x]){
                    int t = a[x];
                    a[x] = a[x << 1];
                    a[x << 1] = t;
                    x <<= 1;
                } else {
                    break;
                }
            }
        }

        public int[] toArray() {
            int[] res = new int[a.length - 1];
            for (int i = 0; i < a.length - 1; ++i){
                res[i] = pop();
            }
            return res;
        }
    }

    void solve() {
        int n = nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = nextInt();
        }
        for (int x : new Heap(a).toArray()){
            out.print(x + " ");
        }
        out.println();
    }
}
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

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    String nextLine() {
        try {
            return br.readLine();
        } catch (Exception e) {
            return "-1";
        }
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "queue1";
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
        Queue q = new Queue();
        int n = nextInt();
        for (int i = 0; i < n; ++i) {
            if (nextToken().charAt(0) == '+') {
                q.push(nextInt());
            } else {
                out.println(q.pop());
            }
        }
    }

    class Vector {
        private int[] a = new int[1];
        private int size = 0;

        private void ensureCapacity(int length) {
            if (length > a.length) {
                int[] b = new int[length * 2];
                for (int i = 0; i < size; ++i) {
                    b[i] = a[i];
                }
                a = b;
            }
        }

        void push(int x) {
            ensureCapacity(size + 1);
            a[size++] = x;
        }

        int get(int pos) {
            return a[pos];
        }

        int length() {
            return a.length;
        }

        Vector(Vector v, int l, int r) {
            int[] b = new int[r - l];
            for (int i = l; i < r; ++i) {
                b[i - l] = v.get(i);
            }
            this.a = b;
            this.size = r - l;
        }

        Vector() {

        }

        int getSize() {
            return size;
        }
    }

    class Queue {
        private Vector v = new Vector();
        private int l = 0, r = 0;

        void push(int x) {
            v.push(x);
            ++r;
        }

        int pop() {
            resize();
            return v.get(l++);
        }

        private void resize() {
            if ((r - l) * 4 < v.length()) {
                v = new Vector(v, l, r);
                l = 0;
                r = v.getSize();
            }
        }
    }
}

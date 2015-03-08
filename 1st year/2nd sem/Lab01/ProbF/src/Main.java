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

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "postfix";
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
        Stack stack = new Stack();
        for (String s = nextToken(); !eof; s = nextToken()) {
            if (s.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (s.equals("-")) {
                stack.push(-stack.pop() + stack.pop());
            } else if (s.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else {
                stack.push(Integer.parseInt(s));
            }
        }
        out.println(stack.pop());
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

        int removeLast() {
            if (size < 1) {
                return -1;
            }
            return a[--size];
        }
    }

    class Stack {
        private Vector v = new Vector();

        void push(int x) {
            v.push(x);
        }

        int pop() {
            return v.removeLast();
        }
    }
}
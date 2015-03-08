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
        String name = "brackets";
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
        abc:
        for (char[] c = nextToken().toCharArray(); !eof; c = nextToken().toCharArray()) {
            Stack stack = new Stack();
            for (int i = 0; i < c.length; ++i) {
                if (c[i] == '(') {
                    stack.push(1);
                } else if (c[i] == ')') {
                    if (stack.peek() != 1) {
                        out.println("NO");
                        continue abc;
                    } else {
                        stack.pop();
                    }
                } else if (c[i] == '[') {
                    stack.push(2);
                } else if (stack.peek() == 2) {
                    stack.pop();
                } else {
                    out.println("NO");
                    continue abc;
                }
            }
            if (stack.isEmpty()){
                out.println("YES");
            } else {
                out.println("NO");
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

        int removeLast() {
            if (size < 1) {
                return -1;
            }
            return a[--size];
        }

        int getLast() {
            if (size < 1) {
                return -1;
            }
            return a[size - 1];
        }

        int size(){
            return size;
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

        int peek() {
            return v.getLast();
        }

        boolean isEmpty(){
            return v.size() == 0;
        }
    }
}
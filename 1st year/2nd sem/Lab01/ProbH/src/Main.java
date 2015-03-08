import java.io.*;
import java.util.*;

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
        String name = "quack";
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

    int md(long x, int mod) {
        if (x < 0) {
            x += (-x / mod + 1) * mod;
        }
        return (int) (x % mod);
    }

    void solve() {
        Queue<String> qList = new Queue<String>();
        int n = 0;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String s = nextToken(); !eof; s = nextToken(), ++n) {
            qList.push(s);
            if (s.charAt(0) == ':') {
                map.put(s.substring(1), n);
            }
        }
        String[] list = new String[n];
        int[] go = new int[n];
        for (int i = 0; i < n; ++i) {
            go[i] = i + 1;
        }
        for (int i = 0; i < n; ++i) {
            list[i] = qList.pop();
            if (list[i].charAt(0) == 'J') {
                go[i] = map.get(list[i].substring(1));
            } else if (list[i].charAt(0) == 'E' || list[i].charAt(0) == 'G') {
                go[i] = map.get(list[i].substring(3));
            } else if (list[i].charAt(0) == 'Z') {
                go[i] = map.get(list[i].substring(2));
            }
        }

        int[] reg = new int[26];
        Queue<Integer> q = new Queue<Integer>();
        int m = 256, m2 = 65536;

        for (int i = 0; i < n; ++i) {
            String s = list[i];
            if (s.equals("+")) {
                q.push(md(q.pop() + q.pop(), m2));
            } else if (s.equals("-")) {
                q.push(md(q.pop() - q.pop(), m2));
            } else if (s.equals("*")) {
                q.push(md((long) q.pop() * q.pop(), m2));
            } else if (s.equals("/")) {
                int a = q.pop(), b = q.pop();
                q.push(b == 0 ? 0 : md(a / b, m2));
            } else if (s.equals("%")) {
                int a = q.pop(), b = q.pop();
                q.push(b == 0 ? 0 : md(md(a, b), m2));
            } else if (s.charAt(0) == '>') {
                reg[s.charAt(1) - 'a'] = q.pop();
            } else if (s.charAt(0) == '<') {
                q.push(reg[s.charAt(1) - 'a']);
            } else if (s.charAt(0) == 'C') {
                if (s.length() == 1) {
                    out.print((char) md(q.pop(), m));
                } else {
                    out.print((char) md(reg[s.charAt(1) - 'a'], m));
                }
            } else if (s.charAt(0) == 'P') {
                if (s.length() == 1) {
                    out.println(q.pop());
                } else {
                    out.println(reg[s.charAt(1) - 'a']);
                }
            } else if (s.charAt(0) == ':') {

            } else if (s.charAt(0) == 'J') {
                i = go[i];
            } else if (s.charAt(0) == 'E') {
                if (reg[s.charAt(1) - 'a'] == reg[s.charAt(2) - 'a']) {
                    i = go[i];
                }
            } else if (s.charAt(0) == 'G') {
                if (reg[s.charAt(1) - 'a'] > reg[s.charAt(2) - 'a']) {
                    i = go[i];
                }
            } else if (s.charAt(0) == 'Z') {
                if (reg[s.charAt(1) - 'a'] == 0) {
                    i = go[i];
                }
            } else if (s.charAt(0) == 'Q') {
                break;
            } else {
                q.push(md(Integer.parseInt(s), m2));
            }
        }
    }

    class Queue<E> {
        private Object[] a = new Object[10];
        private int l = 0, r = 0;

        private void ensureCapacity() {
            if (r >= a.length) {
                Object[] b = new Object[a.length << 1];
                System.arraycopy(a, l, b, l - l, r - l);
                r = r - l;
                l = 0;
                a = b;
            }
        }

        public void push(E x) {
            ensureCapacity();
            a[r++] = x;
        }

        public E pop() {
            return (E) a[l++];
        }
    }
}
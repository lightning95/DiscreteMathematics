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
        String name = "stack2";
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + ".out");
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
        Stack stack = new Stack();
        int n = nextInt();
        for (int i = 0; i < n; ++i) {
            if (nextToken().charAt(0) == '+') {
                stack.push(nextInt());
            } else {
                out.println(stack.pop());
            }
        }
    }

    class List {
        class Item{
            int data;
            Item next;

            Item(int x, Item n){
                data = x;
                next = n;
            }
        }

        private Item head = null;

        void add(int x){
            Item nhead = new Item(x, head);
            head = nhead;
        }

        int getHead(){
            int res = head.data;
            head = head.next;
            return res;
        }
    }

    class Stack {
        private List l = new List();

        void push(int x) {
            l.add(x);
        }

        int pop() {
            return l.getHead();
        }
    }
}
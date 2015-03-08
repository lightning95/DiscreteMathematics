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
        String name = "queue2";
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

    class List {
        private class Item {
            int data;
            Item next;

            Item(int x, Item n) {
                data = x;
                next = n;
            }

            Item() {
                next = null;
            }
        }

        private Item tail, head;

        void add(int x) {
            if (head == null) {
                head = new Item(x, null);
                tail = head;
                return;
            }
            Item t = new Item(x, null);
            tail.next = t;
            tail = t;
        }

        int getHead() {
            int res = head.data;
            head = head.next;
            return res;
        }
    }

    class Queue {
        private List list = new List();

        void push(int x) {
            list.add(x);
        }

        int pop() {
            return list.getHead();
        }
    }
}

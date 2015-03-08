import java.io.*;
import java.util.ArrayList;
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

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "priorityqueue";
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
        Heap heap = new Heap();
        String s = nextToken();
        for (int i = 0; !eof; s = nextToken(), ++i) {
            if (s.equals("push")) {
                heap.push(nextInt(), i);
            } else if (s.equals("extract-min")) {
                out.println(heap.extractMin());
                heap.pos.add(-1);
            } else {
                heap.decreaseKey(nextInt() - 1, nextInt());
                heap.pos.add(-1);
            }
        }
    }

    class Heap {
        private class Pair {
            int x, id;

            Pair(int x, int id) {
                this.x = x;
                this.id = id;
            }
        }

        ArrayList<Pair> a;
        ArrayList<Integer> pos;

        Heap() {
            a = new ArrayList<Pair>();
            pos = new ArrayList<Integer>();
        }

        void push(int x, int id) {
            a.add(new Pair(x, id));
            pos.add(a.size() - 1);

            siftUp(a.size() - 1);
        }

        private void siftUp(int x) {
            if ((x - 1) / 2 >= 0 && a.get(x).x < a.get((x - 1) / 2).x) {
                Pair t = a.get(x);
                a.set(x, a.get((x - 1) / 2));
                a.set((x - 1) / 2, t);

                pos.set(a.get(x).id, x);
                pos.set(a.get((x - 1) / 2).id, (x - 1) / 2);

                siftUp((x - 1) / 2);
            }
        }

        private void siftDown(int x) {
            if (x * 2 + 2 < a.size() && a.get(x * 2 + 1).x > a.get(x * 2 + 2).x && a.get(x * 2 + 2).x < a.get(x).x) {
                Pair t = a.get(x);
                a.set(x, a.get(x * 2 + 2));
                a.set(x * 2 + 2, t);

                pos.set(a.get(x).id, x);
                pos.set(a.get(x * 2 + 2).id, x * 2 + 2);

                siftDown(x * 2 + 2);
                return;
            }
            if (x * 2 + 1 < a.size() && a.get(x * 2 + 1).x < a.get(x).x) {
                Pair t = a.get(x);
                a.set(x, a.get(x * 2 + 1));
                a.set(x * 2 + 1, t);

                pos.set(a.get(x).id, x);
                pos.set(a.get(x * 2 + 1).id, x * 2 + 1);

                siftDown(x * 2 + 1);
            }
        }

        String extractMin() {
            if (a.isEmpty()) {
                return "*";
            }
            int res = a.get(0).x;
            if (a.size() > 1) {
                a.set(0, a.remove(a.size() - 1));
                pos.set(a.get(0).id, 0);
                siftDown(0);
            } else {
                a.remove(0);
            }
            return Integer.toString(res);
        }

        void decreaseKey(int x, int d) {
            int p = pos.get(x);
            int last = a.get(p).x;
            a.set(p, new Pair(d, a.get(p).id));
            if (last > d) {
                siftUp(p);
            } else {
                siftDown(p);
            }
        }
    }
}
import javafx.util.Pair;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 4/29/15.
 */
public class ProbA {
    RW rw;

    class RW {
        private boolean eof;
        StringTokenizer st;
        PrintWriter out;
        BufferedReader br;

        RW(String inputFile, String outputFile) {
            br = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(new OutputStreamWriter(System.out));

            File f = new File(inputFile);
            if (f.exists() && f.canRead()) {
                try {
                    br = new BufferedReader(new FileReader(inputFile));
                    out = new PrintWriter(new FileWriter(outputFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    eof = true;
                    return "-1";
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        void println() {
            out.println();
        }

        void println(Object o) {
            out.println(o);
        }

        void print(Object o) {
            out.print(o);
        }

        void close() {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }

    public static void main(String[] args) {
        new ProbA().run();
    }

    private void run() {
        rw = new RW("schedule.in", "schedule.out");
        solve();
        rw.close();
    }

    private void solve() {
        int n = rw.nextInt();
        Pair<Integer, Integer>[] a = new Pair[n];
        for (int i = 0; i < n; ++i) {
            a[i] = new Pair<>(rw.nextInt(), rw.nextInt());
        }
        Arrays.sort(a, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                if (o1.getKey().equals(o2.getKey())) {
                    return Integer.compare(o2.getValue(), o1.getValue());
                }
                return Integer.compare(o1.getKey(), o2.getKey());
            }
        });

        long ans = 0;
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (Pair<Integer, Integer> p : a) {
            if (q.size() < p.getKey()) {
                q.add(p.getValue());
            } else if (!q.isEmpty() && q.peek() < p.getValue()) {
                ans += q.poll();
                q.add(p.getValue());
            } else {
                ans += p.getValue();
            }
        }
        rw.println(ans);
    }
}

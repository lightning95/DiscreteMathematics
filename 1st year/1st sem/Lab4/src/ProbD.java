
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProbD {
    boolean eof;
    int[] h1 = {2, 1, -2, -1, -1, -2, 1, 2};
    int[] h2 = {1, 2, -1, -2, 2, 1, -2, -1};
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbD().run();
    }

    String nextToken() {
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

    int nextInt() {
        return Integer.parseInt(nextToken());
    }

    long nextLong() {
        return Long.parseLong(nextToken());
    }

    double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    String nextLine() throws IOException {
        return br.readLine();
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "nextsetpartition";
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

    ArrayList<ArrayList<Integer>> sort(ArrayList<ArrayList<Integer>> set, int n) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        int[] id = new int[n];
        Arrays.fill(id, -1);
        for (int i = 0; i < set.size(); ++i) {
            id[set.get(i).get(0)] = i;
        }
        for (int i = 0; i < n; ++i) {
            if (id[i] > -1) {
                res.add(set.get(id[i]));
            }
        }

        return res;
    }

    void solve() throws IOException {
        for (int n = nextInt(), k = nextInt(); n != 0 && k != 0; n = nextInt(), k = nextInt()) {
            ArrayList<ArrayList<Integer>> set = new ArrayList<ArrayList<Integer>>();
            for (int i = 0; i < k; ++i) {
                StringTokenizer s = new StringTokenizer(br.readLine());
                set.add(new ArrayList<Integer>());
                for (; s.hasMoreTokens(); ) {
                    set.get(i).add(Integer.parseInt(s.nextToken()) - 1);
                }
            }
            set = sort(set, n);

            boolean ok = false;
            boolean[] us = new boolean[n];

            abc:
            for (int i = k - 1; i >= 0; --i) {
                ArrayList<Integer> cur = set.get(i);
                for (int j = cur.size() - 1; j >= 0; --j) {
                    for (int x = cur.get(j) + 1; x < n; ++x) {
                        if (us[x] && (j + 1 < cur.size() && x > cur.get(j + 1) || j + 1 == cur.size())) {
                            while (cur.size() > j + 1) {
                                cur.remove(cur.size() - 1);
                            }
                            cur.add(x);
                            us[x] = false;
                            while (set.size() > i + 1) {
                                set.remove(set.size() - 1);
                            }
                            for (int p = 0; p < n; ++p) {
                                if (us[p]) {
                                    set.add(new ArrayList<Integer>());
                                    set.get(set.size() - 1).add(p);
                                }
                            }

                            ok = true;
                            break abc;
                        }
                    }
                    us[cur.get(j)] = true;
                }
            }
            if (ok) {
                out.println(n + " " + set.size());
                for (ArrayList<Integer> subset : set) {
                    for (int i : subset) {
                        out.print(i + 1 + " ");
                    }
                    out.println();
                }
            } else {
                out.println(n + " " + n);
                for (int i = 0; i < n; ++i) {
                    out.println(i + 1 + " ");
                }
            }
            out.println();
        }
    }
}
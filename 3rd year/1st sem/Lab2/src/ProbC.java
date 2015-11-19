import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */
public class ProbC {
    RW rw;
    String FILE_NAME = "minimization";

    public static void main(String[] args) {
        new ProbC().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    void dfs(int u, boolean[] us, int[][] e) {
        us[u] = true;
        for (int i = 0; i < e[u].length; ++i) {
            if (!us[e[u][i]]) {
                dfs(e[u][i], us, e);
            }
        }
    }

    boolean[][] buildNotEq(boolean[] term, List<Integer>[][] rev) {
        int n = term.length;
        boolean[][] notEq = new boolean[n][n];
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (!notEq[i][j] && term[i] != term[j]) {
                    notEq[i][j] = notEq[j][i] = true;
                    q.add(new Pair<>(i, j));
                }
            }
        }
        while (!q.isEmpty()) {
            Pair<Integer, Integer> p = q.remove();
            for (int c = 0; c < 26; ++c) {
                if (rev[p.getKey()][c] == null) {
                    continue;
                }
                for (int i : rev[p.getKey()][c]) {
                    if (rev[p.getValue()][c] == null) {
                        continue;
                    }
                    (rev[p.getValue()][c]).stream().filter(j -> !notEq[i][j]).forEach(j -> {
                        notEq[i][j] = notEq[j][i] = true;
                        q.add(new Pair<>(i, j));
                    });
                }
            }
        }
        return notEq;
    }

    private void solve() {
        int n = rw.nextInt() + 1;
        int m = rw.nextInt();
        int k = rw.nextInt();

        boolean[] term = new boolean[n];
        int[][] e = new int[n][26];
        List<Integer>[][] rev = new ArrayList[n][26];

        for (int j = 0; j < k; ++j) {
            term[rw.nextInt()] = true;
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < 26; ++j) {
                rev[i][j] = new ArrayList<>();
            }
        }

        for (int j = 0; j < m; ++j) {
            int u = rw.nextInt();
            int v = rw.nextInt();
            int c = rw.next().charAt(0) - 'a';
            e[u][c] = v;
        }

        for (int i = 0; i < n; ++i){
            for (int j = 0; j < 26; ++j){
                rev[e[i][j]][j].add(i);
            }
        }

        boolean[] was = new boolean[n];
        dfs(1, was, e);

        boolean[][] notEq = buildNotEq(term, rev);
        int[] component = new int[n];
        Arrays.fill(component, -1);
        for (int i = 0; i < n; ++i) {
            if (!notEq[0][i]) {
                component[i] = 0;
            }
        }
        int compNum = 0;
        for (int i = 1; i < n; ++i) {
            if (!was[i]) {
                continue;
            }
            if (component[i] < 0) {
                component[i] = ++compNum;
                for (int j = i + 1; j < n; ++j) {
                    if (!notEq[i][j]) {
                        component[j] = compNum;
                    }
                }
            }
        }
        compNum++;
        int[][] newE = new int[compNum][26];
        int edgeNum = 0;
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < 26; ++j) {
                if (e[i][j] > 0 && component[i] > 0 && newE[component[i]][j] == 0 && component[e[i][j]] > 0) {
                    newE[component[i]][j] = component[e[i][j]];
                    edgeNum++;
                }
            }
        }

        //
        boolean[] newTerm = new boolean[compNum];
        List<Integer> terms = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (term[i] && component[i] > -1) {
                if (!newTerm[component[i]]) {
                    terms.add(component[i]);
                }
                newTerm[component[i]] = true;
            }
        }

        rw.println(Math.max(compNum - 1, 1) + " " + edgeNum + " " + terms.size());
        for (int i : terms) {
            rw.print(i + " ");
        }
        rw.println();
        for (int i = 0; i < compNum; ++i) {
            for (int j = 0; j < 26; ++j) {
                if (newE[i][j] > 0) {
                    rw.println(i + " " + newE[i][j] + " " + (char) Character.toLowerCase(j + 'a'));
                }
            }
        }
    }

    class RW {
        StringTokenizer st;
        PrintWriter out;
        BufferedReader br;
        private boolean eof;

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
}

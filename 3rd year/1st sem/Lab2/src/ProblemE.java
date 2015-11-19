import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */
public class ProblemE {
    RW rw;
    String FILE_NAME = "cf";
    Lang lang;
    int[][][] dp;

    public static void main(String[] args) {
        new ProblemE().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    int id(char c) {
        return Character.isUpperCase(c) ? c - 'A' : c - 'a' + 26;
    }

    Lang removeLongRules(Lang lang) {
        List<Rule> newRules = new ArrayList<>(lang.allRules.stream().filter(r -> r.a.length < 3)
                .collect(Collectors.toList()));
        int size = lang.sz;
        for (Rule r : lang.allRules) {
            if (r.a.length > 2) {
                int cur = r.term;
                int len = r.a.length;
                for (int i = 0; r.a.length - i > 2; ++i) {
                    newRules.add(new Rule(cur, new int[]{r.a[i], size}));
                    cur = size++;
                }
                newRules.add(new Rule(cur, new int[]{r.a[len - 2], r.a[len - 1]}));
            }
        }
        return new Lang(size, newRules);
    }

    Lang removeEpsRules(Lang l) {
        Set<Integer> set = l.allRules.stream().filter(r -> r.a.length == 0).map(r -> r.term)
                .collect(Collectors.toCollection(TreeSet::new));
        for (boolean ch = true; ch; ) {
            ch = false;
            for (Rule r : l.allRules) {
                if (!set.contains(r.term)) {
                    boolean ok = true;
                    for (int c : r.a) {
                        if (isTerm(c) || !set.contains(c)) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        set.add(r.term);
                        ch = true;
                    }
                }
            }
        }
        // TODO Если в исходной грамматике G выводилось \varepsilon, то необходимо добавить новый нетерминал S',
        // TODO сделать его стартовым, добавить правило S' \rightarrow S|\varepsilon

        List<Rule> newRules = new ArrayList<>(l.allRules.stream().filter(r -> r.a.length > 0).collect(Collectors.toList()));
        l.allRules.stream().filter(r -> r.a.length == 2).forEach(r -> {
            if (set.contains(r.a[0])) {
                newRules.add(new Rule(r.term, new int[]{r.a[1]}));
            }
            if (set.contains(r.a[1])) {
                newRules.add(new Rule(r.term, new int[]{r.a[0]}));
            }
        });
        return new Lang(l.sz, newRules);
    }

    private Lang removeBadRules(Lang lang) {
        List<Rule> newRules = new ArrayList<>();
        int size = lang.sz;
        for (Rule r : lang.allRules) {
            if (r.a.length == 2 && isTerm(r.a[0]) && isTerm(r.a[1])) {
                newRules.add(new Rule(r.term, new int[]{size, size + 1}));
                newRules.add(new Rule(size++, new int[]{r.a[0]}));
                newRules.add(new Rule(size++, new int[]{r.a[1]}));
            } else if (r.a.length == 2 && isTerm(r.a[0])) {
                newRules.add(new Rule(r.term, new int[]{size, r.a[1]}));
                newRules.add(new Rule(size++, new int[]{r.a[0]}));
            } else if (r.a.length == 2 && isTerm(r.a[1])) {
                newRules.add(new Rule(r.term, new int[]{r.a[0], size}));
                newRules.add(new Rule(size++, new int[]{r.a[1]}));
            } else {
                newRules.add(r);
            }
        }
        return new Lang(size, newRules);
    }

    boolean isTerm(int id) {
        return 25 < id && id < 52;
    }

    private void solve() {
        int n = rw.nextInt();
        int start = rw.next().charAt(0) - 'A';

        List<Rule> preRules = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            String s = rw.nextLine();
            if (s.length() > 5) {
                int[] a = new int[s.length() - 5];
                int p = 0;
                for (char c : s.substring(5).toCharArray()) {
                    a[p++] = id(c);
                }
                preRules.add(new Rule(s.charAt(0) - 'A', a));
            } else {
                preRules.add(new Rule(s.charAt(0) - 'A', new int[]{}));
            }
        }

        lang = removeLongRules(new Lang(52, preRules));
        lang = removeEpsRules(lang);
        lang = removeBadRules(lang); // Aa | aA | aa
        int size = lang.sz;
        char[] w = rw.next().toCharArray();
        int m = w.length;
        dp = new int[size][m][m];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < m; ++j) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        for (Rule r : lang.allRules.stream().filter(rule -> rule.a.length == 1 && isTerm(rule.a[0]))
                .collect(Collectors.toList())) {
            for (int i = 0; i < m; ++i) {
                if (r.a[0] == w[i] - 'a' + 26) {
                    dp[r.term][i][i] = 1;
                }
            }
        }

        lang.prepareRules();
        rw.println(rec(start, 0, m - 1) ? "yes" : "no");
    }

    private boolean rec(int u, int l, int r) {
        dp[u][l][r] = 0;
        for (Rule rule : lang.rules[u]) {
            if (rule.a.length == 2) {
                for (int i = l; i < r; ++i) {
                    if ((dp[rule.a[0]][l][i] == -1 ? rec(rule.a[0], l, i) : dp[rule.a[0]][l][i] == 1) &&
                            (dp[rule.a[1]][i + 1][r] == -1 ? rec(rule.a[1], i + 1, r) : dp[rule.a[1]][i + 1][r] == 1)) {
                        return 1 == (dp[u][l][r] = 1);
                    }
                }
            } else if (dp[rule.a[0]][l][r] == -1 ? rec(rule.a[0], l, r) : dp[rule.a[0]][l][r] == 1) {
                return 1 == (dp[u][l][r] = 1);
            }
        }

        return false;
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

        String nextLine() {
            String s = "";
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
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

    class Rule {
        int term;
        int[] a;

        Rule(int t, int[] a) {
            term = t;
            this.a = a;
        }
    }

    class Lang {
        int sz;
        List<Rule> allRules;
        List<Rule>[] rules;

        Lang(int sz, List<Rule> allRules) {
            this.sz = sz;
            this.allRules = allRules;
        }

        void prepareRules() {
            rules = new ArrayList[sz];
            for (int i = 0; i < sz; ++i) {
                rules[i] = new ArrayList<>();
            }
            allRules.stream().filter(r -> !isTerm(r.a[0])).forEach(r -> rules[r.term].add(r));
        }
    }
}

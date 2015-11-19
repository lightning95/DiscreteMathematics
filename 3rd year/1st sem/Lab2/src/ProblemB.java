import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 9/17/15.
 */
public class ProblemB {
    RW rw;
    String FILE_NAME = "epsilon";

    public static void main(String[] args) {
        new ProblemB().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".in", FILE_NAME + ".out");
        solve();
        rw.close();
    }

    private void solve() {
        int n = rw.nextInt();
        int start = rw.next().charAt(0) - 'A';
        List<String>[] r = new ArrayList[26];
        for (int i = 0; i < 26; ++i) {
            r[i] = new ArrayList<>();
        }
        List<Pair<Integer, String>> rules = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            String s = rw.nextLine();
            if (s.length() < 5) {
                r[s.charAt(0) - 'A'].add("");
                rules.add(new Pair<>(s.charAt(0) - 'A', ""));
            } else {
                r[s.charAt(0) - 'A'].add(s.substring(5));
                rules.add(new Pair<>(s.charAt(0) - 'A', s.substring(5)));
            }
        }
        Set<Integer> set = rules.stream().filter(p -> p.getValue().length() == 0)
                .map(Pair::getKey).collect(Collectors.toCollection(TreeSet::new));
        for (boolean ch = true; ch; ) {
            ch = false;
            for (Pair<Integer, String> p : rules) {
                if (!set.contains(p.getKey())) {
                    boolean ok = true;
                    for (char c : p.getValue().toCharArray()) {
                        if (Character.isLowerCase(c) || !set.contains(c - 'A')) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        set.add(p.getKey());
                        ch = true;
                    }
                }
            }
        }
        set.stream().forEach(i -> rw.print((char) (i + 'A') + " "));
        rw.println();
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
}

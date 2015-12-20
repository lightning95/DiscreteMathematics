import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 12/19/15.
 */

public class ProbE {
    RW rw;
    String FILE_NAME = "balanced";

    public static void main(String[] args) {
        new ProbE().run();
    }

    private void run() {
        rw = new RW(FILE_NAME + ".out");
        solve();
        rw.close();
    }

    class RW {
        PrintWriter out;

        RW(String outputFile) {
            try {
                out = new PrintWriter(new FileWriter(outputFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void print(Object o) {
            out.print(o);
        }

        void println(Object o) {
            out.println(o);
        }

        void close() {
            out.close();
        }
    }

    private void solve() {
        rw.print("start: s0\n" +
                "accept: ac\n" +
                "reject: rj\n" +
                "blank: _\n");
        rw.print("s0 _ -> ac _ ^\n" +
                "s0 ( -> s1 _ >\n" +
                "s0 ) -> rj _ ^\n");
        for (int i = 1; i < 100; ++i) {
            rw.print("s" + i + " _ -> rj _ ^\n" +
                    "s" + i + " ( -> s" + (i + 1) + " _ >\n" +
                    "s" + i + " ) -> s" + (i - 1) + " _ >\n");
        }
    }
}

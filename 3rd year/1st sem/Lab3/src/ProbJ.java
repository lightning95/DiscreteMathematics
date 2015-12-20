import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 12/19/15.
 */

public class ProbJ {
    RW rw;
    String FILE_NAME = "postfixlogic";

    public static void main(String[] args) {
        new ProbJ().run();
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
        rw.println(2);
        rw.print("S 1 _ -> S _ > 1 >\n" +
                "S 0 _ -> S _ > 0 >\n");

        rw.print("S o _ -> OR o ^ _ <\n" +
                "OR o 0 -> S _ > _ ^\n" +
                "OR o 1 -> ONE o ^ _ <\n" +
                "ONE o 1 -> S _ > 1 >\n" +
                "ONE o 0 -> S _ > 1 >\n");

        rw.print("S a _ -> AND a ^ _ <\n" +
                "AND a 1 -> S _ > _ ^\n" +
                "AND a 0 -> ZERO a ^ _ <\n" +
                "ZERO a 1 -> S _ > 0 >\n" +
                "ZERO a 0 -> S _ > 0 >\n");

        rw.print("S _ _ -> S _ ^ _ <\n" +
                "S _ 1 -> AC 1 ^ _ ^\n" +
                "S _ 0 -> AC 0 ^ _ ^\n");
    }
}

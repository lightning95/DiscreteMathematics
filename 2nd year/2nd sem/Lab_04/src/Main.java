import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Aydar Gizatullin a.k.a. lightning95, aydar.gizatullin@gmail.com
 *         Created on 5/24/15.
 */
public class Main {
    public static void main(String[] args) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("rainbow.in"));
            out.println("100 5000");
            for (int i = 0; i < 100; ++i) {
                for (int j = 0; j < 50; ++j) {
                    out.println(i + 1 + " " + ((i + j + 1) % 100 + 1) + " " + (j + 1));
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

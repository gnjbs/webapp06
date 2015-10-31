import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * GKislin
 * 23.10.2015.
 */
public class MainFiles {

    public static final String LOG_FILE = "./log/webapp.log.0.1";

    public static void main(String[] args) {
        System.out.println("\nBy BufferedReader\n");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(LOG_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("\nBy stream iterator\n");
            Stream<String> lines = Files.lines(Paths.get(LOG_FILE), StandardCharsets.UTF_8);
            Iterator<String> it = lines.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }


            System.out.println("\nBy stream lambda\n");
            Files.lines(Paths.get(LOG_FILE), StandardCharsets.UTF_8).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(LOG_FILE);
    }
}

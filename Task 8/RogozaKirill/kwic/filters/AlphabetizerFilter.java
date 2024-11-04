package kwic.filters;
import java.io.*;
import java.util.*;

public class AlphabetizerFilter implements Runnable{
    private PipedInputStream in;
    private PipedOutputStream out;

    public AlphabetizerFilter(PipedInputStream in, PipedOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             PrintWriter writer = new PrintWriter(out)) {

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            Collections.sort(lines, String.CASE_INSENSITIVE_ORDER);

            for (String sortedLine : lines) {
                writer.println(sortedLine);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

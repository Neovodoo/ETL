package kwic.filters;
import java.io.*;

public class CircularShifterFilter implements Runnable{

    private PipedInputStream in;
    private PipedOutputStream out;

    public CircularShifterFilter(PipedInputStream in, PipedOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             PrintWriter writer = new PrintWriter(out)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                int wordCount = words.length;
                for (int i = 0; i < wordCount; i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < wordCount; j++) {
                        sb.append(words[(i + j) % wordCount]).append(" ");
                    }
                    writer.println(sb.toString().trim());
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

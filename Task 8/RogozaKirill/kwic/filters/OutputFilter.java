package kwic.filters;
import java.io.*;

public class OutputFilter implements Runnable{

    private PipedInputStream in;

    public OutputFilter(PipedInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

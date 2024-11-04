package kwic.filters;
import java.io.*;
import java.util.*;

// Фильтр ввода
public class InputFilter implements Runnable {
    private PipedOutputStream out;

    public InputFilter(PipedOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(out)) {
            // Захардкоденные строки для примера
            List<String> lines = new ArrayList<>();
            lines.add("Design Patterns in Java");
            lines.add("Key Word in Context");

            for (String line : lines) {
                writer.println(line);
            }
            writer.flush();
        }
    }
}
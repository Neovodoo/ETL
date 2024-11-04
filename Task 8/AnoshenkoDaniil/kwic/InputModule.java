import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputModule {
    private List<String> lines;

    public InputModule() {
        this.lines = new ArrayList<>();
    }

    public void loadLines(String source) {
        try {
            lines = Files.readAllLines(Paths.get(source));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public List<String> getLines() {
        return lines;
    }
}

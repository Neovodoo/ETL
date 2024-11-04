import java.util.ArrayList;
import java.util.List;

public class CircularShiftModule {
    private List<String> shifts;

    public CircularShiftModule() {
        this.shifts = new ArrayList<>();
    }

    public void generateShifts(List<String> lines) {
        for (String line : lines) {
            String[] words = line.split(" ");
            for (int i = 0; i < words.length; i++) {
                shifts.add(createShift(words, i));
            }
        }
    }

    private String createShift(String[] words, int startIndex) {
        StringBuilder shift = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            shift.append(words[(startIndex + i) % words.length]).append(" ");
        }
        return shift.toString().trim();
    }

    public List<String> getShifts() {
        return shifts;
    }
}

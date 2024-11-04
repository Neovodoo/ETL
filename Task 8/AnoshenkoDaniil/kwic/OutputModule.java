import java.util.List;

public class OutputModule {
    public void display(List<String> sortedShifts) {
        for (String shift : sortedShifts) {
            System.out.println(shift);
        }
    }
}

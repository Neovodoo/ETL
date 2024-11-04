import java.util.ArrayList;
import java.util.List;

public class AlphabetizerModule {
    public List<String> sortShifts(List<String> shifts) {
        List<String> sortedShifts = new ArrayList<>(shifts);
        sortedShifts.sort(String::compareToIgnoreCase);
        return sortedShifts;
    }
}

import java.util.List;

public class KWICSystem {
    private final InputModule inputModule;
    private final CircularShiftModule shiftModule;
    private final AlphabetizerModule alphabetizer;
    private final OutputModule outputModule;

    public KWICSystem() {
        this.inputModule = new InputModule();
        this.shiftModule = new CircularShiftModule();
        this.alphabetizer = new AlphabetizerModule();
        this.outputModule = new OutputModule();
    }

    public void run(String source) {
        inputModule.loadLines(source);
        shiftModule.generateShifts(inputModule.getLines());
        List<String> sortedShifts = alphabetizer.sortShifts(shiftModule.getShifts());
        outputModule.display(sortedShifts);
    }

    public static void main(String[] args) {
        KWICSystem kwic = new KWICSystem();
        kwic.run("Task 8/AnoshenkoDaniil/kwic/input.txt");
    }
}

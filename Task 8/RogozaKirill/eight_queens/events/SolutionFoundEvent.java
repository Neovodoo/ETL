package eight_queens.events;
import java.util.List;

public class SolutionFoundEvent {

    private List<Integer> queenPositions;

    public SolutionFoundEvent(List<Integer> queenPositions) {
        this.queenPositions = queenPositions;
    }

    // Геттер
    public List<Integer> getQueenPositions() {
        return queenPositions;
    }

}

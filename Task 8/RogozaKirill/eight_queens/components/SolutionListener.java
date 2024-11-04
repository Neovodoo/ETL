package eight_queens.components;

import com.google.common.eventbus.Subscribe;
import eight_queens.events.*;

import java.util.Arrays;

public class SolutionListener {

    private int solutionCount = 0;

    @Subscribe
    public void handleSolutionFoundEvent(SolutionFoundEvent event) {
        solutionCount++;
        System.out.println("Solution #" + solutionCount + ": " + Arrays.toString(event.getQueenPositions().toArray()));
    }

}

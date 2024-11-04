package eight_queens.components;

import com.google.common.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import eight_queens.events.*;

public class QueenPlacer {

    private int size;
    private EventBus eventBus;

    public QueenPlacer(int size, EventBus eventBus) {
        this.size = size;
        this.eventBus = eventBus;
    }

    public void placeQueens() {
        placeQueenAtRow(0, new int[size]);
    }

    private void placeQueenAtRow(int row, int[] queens) {
        if (row == size) {
            // Найдено решение
            List<Integer> queenPositions = new ArrayList<>();
            for (int col : queens) {
                queenPositions.add(col);
            }
            eventBus.post(new SolutionFoundEvent(queenPositions));
            return;
        }

        for (int col = 0; col < size; col++) {
            if (isSafe(row, col, queens)) {
                // Размещаем ферзя и генерируем событие
                queens[row] = col;
                eventBus.post(new PlaceQueenEvent(row, col));

                placeQueenAtRow(row + 1, queens);

                // После возврата удаляем ферзя и генерируем событие
                eventBus.post(new RemoveQueenEvent(row, col));
                queens[row] = -1;
            }
        }
    }

    private boolean isSafe(int row, int column, int[] queens) {
        for (int i = 0; i < row; i++) {
            int qCol = queens[i];
            if (qCol == column || Math.abs(qCol - column) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

}


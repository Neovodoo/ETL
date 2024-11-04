package eight_queens;

import com.google.common.eventbus.EventBus;
import eight_queens.components.*;

public class EightQueensEventDriven {

    public static void main(String[] args) {

        int size = 8;
        EventBus eventBus = new EventBus();

        Board board = new Board(size);

        // Создаем обработчики событий
        SolutionListener solutionListener = new SolutionListener();
        BoardUpdater boardUpdater = new BoardUpdater(board);

        // Регистрируем обработчики в EventBus
        eventBus.register(solutionListener);
        eventBus.register(boardUpdater);

        // Создаем размещатель ферзей
        QueenPlacer placer = new QueenPlacer(size, eventBus);

        // Начинаем размещение ферзей
        placer.placeQueens();

    }

}

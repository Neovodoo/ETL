package eight_queens.components;

import com.google.common.eventbus.Subscribe;
import eight_queens.events.*;

public class BoardUpdater {

    private Board board;

    public BoardUpdater(Board board) {
        this.board = board;
    }

    @Subscribe
    public void handlePlaceQueenEvent(PlaceQueenEvent event) {
        board.placeQueen(event.getRow(), event.getColumn());
    }

    @Subscribe
    public void handleRemoveQueenEvent(RemoveQueenEvent event) {
        board.removeQueen(event.getRow());
    }

}

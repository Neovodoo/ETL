package eight_queens.events;

public class RemoveQueenEvent {

    private int row;
    private int column;

    public RemoveQueenEvent(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}

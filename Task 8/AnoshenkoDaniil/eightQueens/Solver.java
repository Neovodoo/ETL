public class Solver {
    private final int size;

    public Solver(int size) {
        this.size = size;
    }

    public boolean solve(Board board, int row) {
        if (row == size) return true;
        for (int col = 0; col < size; col++) {
            if (board.isSafe(row, col)) {
                board.placeQueen(row, col);
                if (solve(board, row + 1)) return true;
                board.removeQueen(row, col);
            }
        }
        return false;
    }
}

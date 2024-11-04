public class Board {
    private final int size;
    private final boolean[][] board;

    public Board(int size) {
        this.size = size;
        this.board = new boolean[size][size];
    }

    public boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i][col]) return false;
        }
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j]) return false;
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < size; i--, j++) {
            if (board[i][j]) return false;
        }
        return true;
    }

    public void placeQueen(int row, int col) {
        board[row][col] = true;
    }

    public void removeQueen(int row, int col) {
        board[row][col] = false;
    }

    public boolean[][] getBoardState() {
        return board;
    }
}

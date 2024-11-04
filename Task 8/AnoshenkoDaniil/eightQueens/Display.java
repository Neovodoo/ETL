public class Display {
    public void displayBoard(Board board) {
        boolean[][] state = board.getBoardState();
        for (boolean[] row : state) {
            for (boolean cell : row) {
                System.out.print(cell ? "Q " : ". ");
            }
            System.out.println();
        }
    }
}

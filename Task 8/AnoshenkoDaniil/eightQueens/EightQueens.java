public class EightQueens {
    public static void main(String[] args) {
        int size = 8;
        Board board = new Board(size);
        Solver solver = new Solver(size);
        Display display = new Display();

        if (solver.solve(board, 0)) {
            display.displayBoard(board);
        } else {
            System.out.println("No solution found.");
        }
    }
}

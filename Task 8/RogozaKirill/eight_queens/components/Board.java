package eight_queens.components;

import java.util.Arrays;

public class Board {

    private int size;
    private int[] queens; // Индекс массива - строка, значение - колонка

    public Board(int size) {
        this.size = size;
        this.queens = new int[size];
        Arrays.fill(queens, -1); // Инициализируем массив значениями -1 (ферзь не размещен)
    }

    public void placeQueen(int row, int column) {
        queens[row] = column;
    }

    public void removeQueen(int row) {
        queens[row] = -1;
    }

}

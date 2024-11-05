class chess_board:
    def __init__(self, size):
        self.size = size
        self.queens = []  # позиции королев - строка, колонка
    # проверка на то что место безопасно
    def is_safe(self, row, col):
        for q_row, q_col in self.queens:
            if q_col == col or q_row - q_col == row - col or q_row + q_col == row + col:
                return False
        return True

    def place_queen(self, row, col):
        self.queens.append((row, col))

    def remove_queen(self, row, col):
        self.queens.remove((row, col))
    # вывод доски матрицей
    def print_board(self):
        size = self.size
        board = [['-' for i in range(size)] for j in range(size)]
        for row, col in self.queens:
            board[row][col] = 'Q'
        # для нормального вывода
        for row in board:
            print(' '.join(row))


class main:
    def __init__(self, size):
        self.size = size
        self.board = chess_board(size)
        self.solutions = []

    def solve(self, row=0):
        if row == self.size:
            # нашли решение
            self.solutions.append(self.board.queens.copy())
            return
        for col in range(self.size):
            if self.board.is_safe(row, col):
                self.board.place_queen(row, col)
                self.solve(row + 1)
                self.board.remove_queen(row, col)

    def get_solutions(self):
        return self.solutions
    # выводим решение
    def print_solutions(self):
        for id_solution, solution in enumerate(self.solutions, start=1):
            print("Solution", id_solution, ':')
            self.board.queens = solution
            self.board.print_board()
            print()


# запускаем
size = 8
solve = main(size)
solve.solve()
print("Total solutions:", len(solve.get_solutions()), '\n')
solve.print_solutions()

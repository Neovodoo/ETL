from typing import List

#1: Creates an empty board
def initialize_filter(n: int) -> List[List[str]]:
    return [["." for _ in range(n)] for _ in range(n)]

#2: Places queen "Q" in row and column
def row_filter(board: List[List[str]], row: int, col: int) -> List[List[str]]:
    new_board = [row[:] for row in board]  # Make a deep copy of the board
    new_board[row][col] = "Q"
    return new_board

#3: Checks if a queen at (row, col) conflict or not
def safety_check_filter(board: List[List[str]], row: int, col: int, n: int) -> bool:
    #column
    for i in range(row):
        if board[i][col] == "Q":
            return False
    #left diagonal
    for i, j in zip(range(row - 1, -1, -1), range(col - 1, -1, -1)):
        if board[i][j] == "Q":
            return False
    #right diagonal
    for i, j in zip(range(row - 1, -1, -1), range(col + 1, n)):
        if board[i][j] == "Q":
            return False
    return True

#4: placing queens row by row
def backtracking_filter(board: List[List[str]], row: int, n: int, solutions: List[List[List[str]]]):
    if row == n:
        solutions.append([row[:] for row in board])  # Append a deep copy of the board as a solution
    else:
        for col in range(n):
            if safety_check_filter(board, row, col, n):
                new_board = row_filter(board, row, col)
                backtracking_filter(new_board, row + 1, n, solutions)

# Step 5: Solution Collector Filter - Collects and prints solutions
def solution_collector_filter(solutions: List[List[List[str]]]):
    print(f"Number of solutions: {len(solutions)}\n")
    for solution in solutions:
        for row in solution:
            print(" ".join(row))
        print("\n")

# runcode
def solve_n_queens(n: int = 8):
    board = initialize_filter(n)
    solutions = []
    backtracking_filter(board, 0, n, solutions)
    solution_collector_filter(solutions)

# implementation
solve_n_queens(8)

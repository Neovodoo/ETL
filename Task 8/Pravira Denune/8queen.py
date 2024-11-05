from typing import List

#1: Start with an empty board
def initialize_filter(n: int) -> List[int]:
    return [-1] * n  #Use -1 for no queen is placed in any column for each row

#2: Place a queen in a new row
def queen_filter(board: List[int], row: int, col: int) -> List[int]:
    new_board = board[:]
    new_board[row] = col
    return new_board

#3: Check if placing a queen at (row, col) is safe
def queen_check_filter(board: List[int], row: int) -> bool:
    for i in range(row):
        # Check column and diagonal if there is some conflicts
        if board[i] == board[row] or abs(board[i] - board[row]) == abs(i - row):
            return False
    return True

#4: Recursively try placing queens row by row
def backtracking_filter(board: List[int], row: int, n: int, solutions: List[List[int]]):
    if row == n:
        solutions.append(board[:])  # the solution show up
    else:
        for col in range(n):
            new_board = queen_filter(board, row, col)
            if queen_check_filter(new_board, row):
                backtracking_filter(new_board, row + 1, n, solutions)

#5: check the solutions for the puzzle
def solution_collector_filter(solutions: List[List[int]]):
    print(f"Number of solutions: {len(solutions)}")
    for solution in solutions:
        print(solution)

# put function
def solve_queens(n: int):
    board = initialize_filter(n)
    solutions = []
    backtracking_filter(board, 0, n, solutions)
    solution_collector_filter(solutions)

# implementation
solve_queens(8)

from collections import deque

n = int(input("Input size: "))

def place_queen(row, board, solutions, events):  
    if row == n:
        events.append(('solution', board[:]))
        return

    for col in range(n):
        if check(row, col, board):
            board.append((row, col))
            events.append(('place_queen', row + 1, board[:], solutions, events))
            board.pop()

def check(row, col, board):
    for r, c in board:
        if c == col or abs(c - col) == abs(r - row):
            return False
    return True

def solve_eight_queens():
    board = []
    solutions = []
    events = deque()

    events.append(('place_queen', 0, board, solutions, events))

    # Event loop
    while events:
        event = events.popleft()
        event_type = event[0]

        if event_type == 'place_queen':
            _, row, board, solutions, events = event
            place_queen(row, board, solutions, events)
        
        elif event_type == 'solution':
            _, solution = event
            solutions.append(solution)

    return solutions

# Run the event-driven Eight Queens solver
solutions = solve_eight_queens()
for solution in solutions:
    print(solution)

print("Number of solutions:", len(solutions))



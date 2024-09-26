import sys


def find_neighbours(board, row, column):
    all_coordinates = []
    if board[row][column] == " ":
        return all_coordinates
    last_extension = [[row, column]]
    while last_extension:  # loops until there is no number left to extend.
        temp_extension = []
        for coord in last_extension:  # extends all_coor list with last_extension.
            if coord not in all_coordinates:
                all_coordinates.append(coord)
                temp_extension.extend(check_neighbours(board, *coord))  # gets new neighbours into last extensiom
        last_extension = temp_extension
    return all_coordinates
3

def set_board(board, all_coord):
    """gets row and colum number and sets board.Returns score gained"""
    for r, c in all_coord:
        board[r][c] = " "
    # Remove colum if it is empty

    empty_columns = []
    flag = False
    for c in range(len(board[0])):  # finds empty columns.
        for r in range(len(board)):
            if board[r][c] != " ":
                flag = True
                break
        if flag:
            flag = False
            continue
        empty_columns.append(c)

    empty_rows = []
    for row in board:
        for number in row:
            if number != " ":
                flag = True
                break
        if flag:
            flag = False
            continue
        empty_rows.append(row)

    empty_rows.reverse()
    for row in empty_rows:
        board.remove(row)

    empty_columns.sort(reverse=True)  # deletes last colum to first one.
    for columns in empty_columns :  # deletes empty columns.
        for rows in range(len(board)):
            del board[rows][columns]

    finished = False
    while not finished:  # drops the numbers.
        finished = True
        for r in range(len(board) - 1):
            for c in range(len(board[0])):
                if board[r][c] != " " and board[r + 1][c] == " ":  # if a numbers below is empty drops the number.
                    board[r + 1][c] = board[r][c]
                    board[r][c] = " "
                    finished = False


    return


def check_neighbours(board, row, colum):
    """check 4 neighbours of given coordinate"""
    coordinates = []
    base_number = board[row][colum]
    for i, t in [(1,0), (-1,0), (0,-1), (0,1)]:  # i for left right colum. t for left right row
        if 0 <= colum+i <= len(board[row])-1 and 0<= row+t <= len(board)-1:
            if base_number == board[row+t][colum+i]:
                coordinates.append([row+t, colum+i])
    return coordinates


def is_game_over(board):
    """checks is games over. returns True/False"""
    for row in range(len(board)):
        for col in range(len(board[0])):
            if check_neighbours(board, row, col) and board[row][col] != " ":
                return False
    return True


def print_board(board):
    """prints the board"""
    for line in board:
        print(" ".join(line))
    print()


def main():
    with open(sys.argv[1], "r") as file:
        input_ = file.readlines()
        board = []
        for line in input_:
            board.append(line.strip().split())
    score = 0
    game_over = False
    print_board(board)
    print(f"Your score is: {score}")
    while not game_over:
        print()
        row_clm = input("Please enter a row and a column number: ")
        print()
        try:
            row_number, clm_number = int(row_clm[0])-1, int(row_clm[2])-1
            if 0<= row_number <= len(board)-1 and 0<= clm_number <= len(board[row_number])-1:  # check if given coord.
                all_coor = find_neighbours(board, row_number, clm_number)                           # in board
                if len(all_coor) > 1:
                    score += len(all_coor) * int(board[row_number][clm_number])  # calculates score
                    set_board(board, all_coor)
                else:
                    print("No movement happened try again\n")
            else:
                print("Please enter a correct size!")
                continue
        except ValueError:
            print("Please give numbers\n")
        print_board(board)
        print(f"Your score is: {score}")
        game_over = is_game_over(board)
    print()
    print("Game Over")

main()
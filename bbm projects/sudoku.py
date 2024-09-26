import sys


def cube_searching(row, colum, sudoku_diagram):
    """searches corresponding CUBE and gives numbers in the cube as a list"""
    numbers_in_exist_cube = []

    cube_row_label = row // 3+1    # cube rows labeled as 1 , 2 , 3
    cube_colum_label = colum // 3+1      # cube columns labeled as 1 , 2 , 3

    for row_x in range((cube_row_label-1) * 3, 3*cube_row_label):            # indexing rows
        for colum_x in range((cube_colum_label-1)*3, cube_colum_label*3):  # and columns which corresponds to labels
            indexed_number = sudoku_diagram[row_x][colum_x]
            if indexed_number != "0":      # excluding "0" from list
                numbers_in_exist_cube.append(indexed_number)  # adds numbers of the current cube to list
    return numbers_in_exist_cube


def colum_searching(colum, sudoku_diagram):
    """searches corresponding COLUM and gives numbers in the colum as a list"""
    numbers_in_exist_colum = []
    for row_x in range(9):  # each row in current colum
        indexed_number = sudoku_diagram[row_x][colum]  # indexes the number in specified colum and row
        if indexed_number != "0":   # excluding "0" from list
            numbers_in_exist_colum.append(indexed_number)  # add indexed number to the list
    return numbers_in_exist_colum


def row_searching(row,sudoku_diagram):
    """searches corresponding ROW and gives numbers in the row as a list"""
    numbers_in_exist_row = []
    for colum_x in range(9):  # each colum in current row
        indexed_number = sudoku_diagram[row][colum_x]  # indexes the number in specified colum and row
        if indexed_number != "0":  # excluding "0" from list
            numbers_in_exist_row.append(indexed_number)  # add indexed number to the list
    return numbers_in_exist_row


def searching(row, colum, sudoku_diagram):
    """searches row,col,cube and adds different numbers to a list. returns the list"""
    nums_in_cube = cube_searching(row,colum,sudoku_diagram)
    nums_in_colum = colum_searching(colum,sudoku_diagram)
    nums_in_row = row_searching(row,sudoku_diagram)
    united_list = nums_in_cube+nums_in_colum+nums_in_row  # unites the lists of row,colum,and cube

    exist_numbers = []
    for number in united_list:    # Adds each number in the united_list to the exist_numbers list only once
        if number not in exist_numbers:
            exist_numbers.append(number)
    return exist_numbers


def missing_number(number_list):
    """returns number that are not in the list 1 to 9. returns in str format"""
    for number in range(1, 10):
        if str(number) not in number_list:
            return str(number)


def writing_output(sn, val, row, col, sdk, output_file):
    """edits the arguments and writes them to the output.txt file"""
    dash = "------------------"
    output_file.write(f"{dash}\nStep {sn} - {val} @ R{row+1}C{col+1}\n{dash}\n")  # writes sudoku placement information.
    for row_w in range(9):  # sudoku diagram line by line.
        organized_sudoku_line = " ".join(sdk[row_w])  # edits line format, adds spaces between numbers.
        output_file.write(organized_sudoku_line+"\n")  # writes edited sudoku line 9 times and creates diagram.


def main():
    input_file = open(sys.argv[1], "r")
    output_file = open(sys.argv[2], "w")
    sudoku_diagram = []
    for line in input_file:
        line = line.strip().split()  # strips and splits lists for making list of lists, this allows easy indexing
        sudoku_diagram.append(line)  # creates a sudoku diagram from txt
    input_file.close()

    completed_box = 0
    step_number = 0
    while completed_box < 81:  # Checks the number of boxes to see if the game is over.
        completed_box = 0  # resets the completed box counter
        break_flag = False  # for breaking double for loop one flag is used.
        for row in range(9):
            if break_flag:
                break
            for colum in range(9):
                if sudoku_diagram[row][colum] == "0":  # checks each box if its empty or resolved.
                    all_numbers = searching(row, colum, sudoku_diagram)  # description writes on docstring.
                    if len(all_numbers) == 8:  # there can be 9 different numbers in list.
                        missing_num = missing_number(all_numbers)  # absence of only one element leads to definite solve
                        sudoku_diagram[row][colum] = missing_num
                        step_number += 1
                        writing_output(step_number, missing_num, row, colum, sudoku_diagram, output_file)
                        break_flag = True  # break flag for breaking 2 for loops.
                        break
                else:
                    completed_box += 1  # if there is no empty boxes left this number reaches to 81
    output_file.write("------------------")
    output_file.flush()
    output_file.close()


if __name__ == "__main__":
    main()



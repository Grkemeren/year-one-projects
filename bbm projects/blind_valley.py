import sys


def read_file():
    """reads and organise file. Returns: list of number restrictions, list of diagram letters."""
    with open(sys.argv[1], "r") as input_file:
        input_text = []
        for line in input_file:
            input_text.append(line.strip().split())
        number_rest = input_text[:4]
        diagram = input_text[4:]
    return number_rest, diagram


def write_file(text):
    """take list of list and writes line by line"""
    with open(sys.argv[2], "w") as output_file:
        if not text:
            output_file.write("No solution!")
        else:
            organized_lines = []
            for line in text:
                organized_lines.append(" ".join(line))
            output_file.write("\n".join(organized_lines))
    return


def check_rows_restrictions(number_rest, diagram, i_row):
    """ Checks whether the puzzle fits within the restrictions for SİNGLE ROW.
        args:
            number_rest(list of list): list of restrictions
            diagram(list of list): the games diagram

        returns: bool
    """
    left_high = number_rest[0]
    right_base = number_rest[1]

    if not (left_high[i_row] == "-1" or int(left_high[i_row]) == diagram[i_row].count("H")):
        return False
    if not (right_base[i_row] == "-1" or int(right_base[i_row]) == diagram[i_row].count("B")):
        return False
    return True


def check_cols_restriction(number_rest, diagram):
    """ Checks whether the puzzle fits within the restrictions FOR ALL COLUMNS.
        args:
            number_rest(list of list): list of restrictions
            diagram(list of list): the games diagram

        returns: bool
    """
    up_high = number_rest[2]
    down_base = number_rest[3]

    col_control_diagram = [list(i) for i in zip(*diagram)]

    # col control
    for i_col in range(len(col_control_diagram)):
        if not (up_high[i_col] == "-1" or int(up_high[i_col]) == col_control_diagram[i_col].count("H")):
            return False
        if not (down_base[i_col] == "-1" or int(down_base[i_col]) == col_control_diagram[i_col].count("B")):
            return False
    return True


def check_rectangle_neighbours(row_n, clm_n, diagram, hbn_letter):
    """ checks whether there are elements equivalent to the given coordinate, below, above, to the right, to the left.
        args:
            row_n(int): row index
            clm_n(int): clm index
            hbn_letter(str): the controlled element in the middle
        returns: bool
    """
    if hbn_letter == "N":
        return True
    for r, c in [(0, 1), (0, -1), (1, 0), (-1, 0)]:  # r,c = relative index.
        if 0 <= row_n + r <= len(diagram) - 1 and 0 <= clm_n + c <= len(diagram[0]) - 1:
            if diagram[row_n + r][clm_n + c] == hbn_letter:  # if relative index matches with hbn_letter,return false
                return False
    return True


def backtrack(diagram, result, number_rest, row, col, hbn):
    """ If the values are appropriate, it places h b n in the next rectangle, if the wrong placement is made,
        undoes the changes and continues to be tried.
        :args:
            row(int): row index starts from 0.
            col(int): col index starts from 0.
            hbn(str): indicates the letter to be placed in the rectangle

        :returns
            bool
    """
    # base cases start
    if result:  # if any true results are found
        return True

    if hbn == "start":  # first starter , will work only once
        return ((backtrack(diagram, result, number_rest, 0, 0, "H") or
                 backtrack(diagram, result, number_rest, 0, 0, "B") or
                 backtrack(diagram, result, number_rest, 0, 0, "N")))

    if col == 0 and row != 0:  # it checks the previous line's restrictions.
        if check_rows_restrictions(number_rest, diagram, row - 1) == False:
            return False

    if col == len(diagram[0]) - 1:
        if row == len(diagram) - 1:  # in last diagram box, checks if the result is proper.
            if check_rows_restrictions(number_rest, diagram, row) and check_cols_restriction(number_rest, diagram):
                result.extend([list(i) for i in diagram])  # copies diagram to result
                return True
            else:  # base case finish
                return False
        next_row, next_col = row + 1, 0
    else:
        next_row, next_col = row, col + 1

    if diagram[row][col] == "L":  # determines where to place hbn letters.
        r = 0
        c = 1
        before_change = "L", "R"
    elif diagram[row][col] == "U":
        r = 1
        c = 0
        before_change = "U", "D"
    else:
        return backtrack(diagram, result, number_rest, next_row, next_col, hbn)  # go next coordinate.
    if hbn == "H":
        hbn_2 = "B"  # determines the hbn letters pair.
    elif hbn == "B":
        hbn_2 = "H"
    else:
        hbn_2 = "N"

    if (check_rectangle_neighbours(row, col, diagram, hbn) and
            check_rectangle_neighbours(row + r, col + c, diagram, hbn_2)):  # if it is suitable for determined change,

        diagram[row][col] = hbn  # makes the changes. else returns false
        diagram[row + r][col + c] = hbn_2

        first_branch = backtrack(diagram, result, number_rest, next_row, next_col, "H")  # tree recursion.

        second_branch = backtrack(diagram, result, number_rest, next_row, next_col, "B")

        third_branch = backtrack(diagram, result, number_rest, next_row, next_col, "N")

        diagram[row][col], diagram[row + r][col + c] = before_change  # undoes the changes made in exist node.

        return first_branch or second_branch or third_branch
    else:
        return False


def main():
    number_rest, diagr = read_file()
    result = []
    statement = backtrack(diagr, result, number_rest, 0, 0, "start")  # if a result founded it's True.
    write_file(result)


if __name__ == '__main__':
    main()

import sys


def input_file():
    """if argv count not equal 2 returns False. opens input file, after organization returns is;
    if there is not a file returns False """
    if len(sys.argv) - 1 != 2:
        print("ERROR: This program needs two command line arguments to run, where first one is the input file and the second one is the output file")
        print("Sample run command is as follows: python3 calculator.py input.txt output.txt")
        print("Program is going to terminate!")
        return False
    try:
        with open(sys.argv[1], "r") as file:
            input = file.readlines()
            calculations = []
            for line in input:
                updated_line = line.strip().split()
                if updated_line: calculations.append(updated_line)  # if line isn't empty appends line.
        return calculations
    except FileNotFoundError:
        print(f"ERROR: There is either no such a file namely {sys.argv[1]} or this program does not have permission to read it!")
        print("Program is going to terminate!")
        return False


def calculate(calculations, out_text):
    operations = {
        "+": lambda x, y: x + y,
        "-": lambda x, y: x - y,
        "*": lambda x, y: x * y,
        "/": lambda x, y: x / y
    }
    for calc in calculations:
        out_text.append(" ".join(calc))  # writes the calculation.
        if len(calc) != 3:
            out_text.append("ERROR: Line format is erroneous!")
        else:
            try:
                OP1 = float(calc[0])
            except Exception:  # if operand_1 is not a number appends error message and continue to next calculation.
                out_text.append("ERROR: First operand is not a number!")
                continue
            try:
                OP2 = float(calc[2])
            except Exception:  # if operand_2 is not a number appends error message and continue to next calculation.
                out_text.append("ERROR: Second operand is not a number!")
                continue

            try:
                operator = calc[1]
                result = operations[operator](x=OP1, y=OP2) # if it gives a KeyError then operation is not in dictionary
                rounded_result = round(result, 2)
                format_changed_result = "{:.2f}".format(rounded_result)  # formats the 2.0 to 2.00
                out_text.append("="+str(format_changed_result))
            except Exception:
                out_text.append("ERROR: There is no such an operator!")


def main():
    calculations = input_file()
    if not calculations: return  # if argv format isn't correct or no file found terminates the program.
    output_text_list = []  # collects output lines as a list.
    calculate(calculations, output_text_list)  # makes calculations and append the results into given file.

    output_file = open(sys.argv[2], "w")
    output_file.write("\n".join(output_text_list))  # adds new_line between lines and writes them.
    output_file.flush()
    output_file.close()


if __name__ == '__main__':
    main()

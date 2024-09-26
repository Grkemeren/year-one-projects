import sys


def sort_control(alist):
    """checks if the list sorted. returns False, True"""
    for i in range(len(alist) - 1):
        if alist[i] > alist[i + 1]:
            return False
    return True


def insertion_sort(int_list, output_file):
    """sorts the list and writes output into given file"""
    insertion_list = int_list[:]
    step_number = 1
    for position in range(1, len(insertion_list)):  # from 2. number to last number
        current_number = insertion_list[position]

        while current_number < insertion_list[position - 1] and position > 0:  # checks if number is lesser than before
            insertion_list[position] = insertion_list[position - 1]
            position -= 1
        insertion_list[position] = current_number

        if sort_control(insertion_list):  # checks if sort completed at every scan.
            write_last_line(step_number, insertion_list, output_file)
            return insertion_list
        else:
            writing_output(step_number, insertion_list, output_file)
            step_number += 1


def bubble_sort(int_list, output_file):
    """sorts the list and writes output into given file"""
    bubble_list = int_list[:]
    scan_range = len(bubble_list)
    step_number = 1
    sorted_list = False
    while not sorted_list:
        for i in range(scan_range - 1):
            if bubble_list[i] > bubble_list[i + 1]:
                bubble_list[i], bubble_list[i + 1] = bubble_list[i + 1], bubble_list[i]  # swapping numbers

        if sort_control(bubble_list):  # checks if sort completed at every scan.
            write_last_line(step_number, bubble_list, output_file)
            return bubble_list
        else:
            writing_output(step_number, bubble_list, output_file)
            step_number += 1
            scan_range -= 1


def write_last_line(pn, cur_lst, output_file):
    str_list = [str(x) for x in cur_lst]  # be able to use join method making a str list.
    organized_list = " ".join(str_list)
    output_file.write(f"Pass {pn}: {organized_list}")  # no new line.


def writing_output(pn, cur_lst, output_file):
    str_list = [str(x) for x in cur_lst]  # be able to use join method making a str list.
    organized_list = " ".join(str_list)
    output_file.write(f"Pass {pn}: {organized_list}\n")


def main():
    input_file = open(sys.argv[1], "r")
    input_list = input_file.readline().split()
    int_input_list = [int(x) for x in input_list[:]]  # for comparing list converted to list of integers.
    input_file.close()
    bubble_output = open(sys.argv[2], "w")
    insertion_output = open(sys.argv[3], "w")

    if sort_control(int_input_list):
        bubble_output.write("Already sorted!")
        insertion_output.write("Already sorted!")
    else:
        bubble_sort(int_input_list, bubble_output)
        insertion_sort(int_input_list, insertion_output)
    bubble_output.flush()
    bubble_output.close()
    insertion_output.flush()
    insertion_output.close()

if __name__ == "__main__":
    main()

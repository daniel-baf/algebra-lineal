import sys, os

from Parser.ComplexLexer import make_lexer
from Parser.ComplexParser import make_parser


def main():
    menu()


def clear_screen():
    pass  # TODO implement a clear screen


def solve_equation(data):
    try:
        lexer = make_lexer()
        parser = make_parser()
        print("------------- SOLVING -------------")
        result = parser.parse(data, lexer=lexer)
        print(result)
        result.solve(result)
    except Exception as e:
        print(f"Unable to solve eqq {data}, error: {e}")


def enter_equation():
    equation = input("Enter the equation: ")
    print(equation)
    clear_screen()
    solve_equation(equation)


def read_file():
    try:
        file_path = 'input.txt'  # Replace with your actual file path
        clear_screen()
        with open(file_path, 'r') as file:
            # Iterate over each line in the file
            for line in file:
                # Process each line (you can replace this with your own logic)
                solve_equation(line.strip())  # strip() removes leading and trailing whitespaces
    except FileNotFoundError:
        print("eqq.txt not found.")


def menu():
    while True:
        print("Menu:")
        print("1. To enter an equation")
        print("2. To read file eqq.txt (must be created)")
        print("0. Exit")

        selection = input("->\t")
        # Execute the selected function
        if selection == "1":
            enter_equation()
        elif selection == "2":
            read_file()
        elif selection == "0":
            sys.exit()
        else:
            print("Invalid option")


if __name__ == "__main__":
    menu()

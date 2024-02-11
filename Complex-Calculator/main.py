import sys, os

from Parser.ComplexLexer import make_lexer
from Parser.ComplexParser import make_parser


# solves the equation, if it can't solve it, it will print an error
# receive string to solve, called from enter_equation and read_file
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


# reads data from the console eqq as 1+2/(21-1)...
def enter_equation():
    equation = input("Enter the equation: ")
    print(equation)
    solve_equation(equation)


# reads data from input.txt (must be a local path file, same level as main.py)
def read_file():
    try:
        file_path = 'input.txt'  # Replace with your actual file path
        with open(file_path, 'r') as file:
            # Iterate over each line in the file
            for line in file:
                # Process each line (you can replace this with your own logic)
                solve_equation(line.strip())  # strip() removes leading and trailing whitespaces
    except FileNotFoundError:
        print("eqq.txt not found.")


# display a menu and exit when the user selects 0
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

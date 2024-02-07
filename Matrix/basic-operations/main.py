import numpy as np
from Utils.PrinterManager import PrinterManager as Printer
from Parser.MatrixLexer import make_lexer
from Parser.MatrixParser import make_parser

def read_file(file_path: str) -> str:
    try:
        with open(file_path, "r") as file:
            return file.read()
    except Exception as e:
        Printer.custom_print(f'Error reading file {e}')


def execute():
    try:
        lexer = make_lexer()
        parser = make_parser()
        data = read_file("matrix.txt")
        # check if data ends with \n if not, add it
        result = parser.parse(f"{data}", lexer=lexer)
        # PRINT A SUMMARY OF MATRICES
        Printer.custom_print("------- MATRICES -------\n")
        for matrix in result["matrices"]:
            print(matrix)
        # create a for to loop thoug result["operations"] and add an index
        for index, operation in enumerate(result["operations"]):
            Printer.custom_print(f"\n------- REALIZANDO OPERACION {index + 1} -------\n")
            print(operation)
            result = operation.solve(operation)
            Printer.custom_print(f"\n++++++ RESULTADO OPERACION {index + 1} ++++++\n")
            Printer.custom_print(result)
    except Exception as e:
        Printer.custom_print(f'Error executing code {e} ensure there is a JUMPLINE at end of file')

def main():
    try:
        execute()
    except Exception as e:
        Printer.custom_print(f'Error: {e}')


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()


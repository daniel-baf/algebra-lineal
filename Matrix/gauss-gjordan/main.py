import numpy as np
from Parser.MatrixLexer import make_lexer
from Parser.MatrixParser import make_parser
from Utils.PrinterManager import PrinterManager as Printer


def read_file(file_path: str) -> str:
    try:
        with open(file_path, "r") as file:
            return file.read()
    except Exception as e:
        Printer.custom_print(f'No se ha podidio encontrar el archivo {file_path}', e)


def find_matrix(matrix_name: str, matrices: list):
    for matrix in matrices:
        if matrix.name == matrix_name:
            return matrix
    return None


def execute():
    try:
        lexer = make_lexer()
        parser = make_parser()
        data = read_file("matrix.txt")
        # check if data ends with \n if not, add it
        result = parser.parse(f"{data}", lexer=lexer)
        for gauss_token in result["operations"]:
            gauss_token.solve(result["matrices"])
    except Exception as e:
        Printer.custom_print(f'Error al ejecutar el codigo: {e}\nAsegurate de que el archivo exista y que termine en salto de linea')


def main():
    try:
        execute()
    except Exception as e:
        Printer.custom_print(f'Error: {e}')


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()

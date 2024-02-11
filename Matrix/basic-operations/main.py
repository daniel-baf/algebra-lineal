import numpy as np
from Utils.PrinterManager import PrinterManager as Printer
from Parser.MatrixLexer import make_lexer
from Parser.MatrixParser import make_parser

# READ DATA from file, file must be named 
def read_file(file_path: str) -> str:
    try:
        with open(file_path, "r") as file:
            return file.read()
    except Exception as e:
        Printer.custom_print(f'Error al leer el archivo {file_path}\n Error: {e}')


# execute the app, creates lexer, parser, handle data and print steps
def execute():
    try:
        # lexer & parser generation and execution
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
        Printer.custom_print(f'Uy, hay un error al ejecutar el codigo {e} Asegurate de que el archivo matrix.txt exista y tenga el formato correcto.\nDebe terminar con un SALTO DE LINEA [JMPLN]')

def main():
    try:
        execute()
    except Exception as e:
        Printer.custom_print(f'Error: {e}')


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()


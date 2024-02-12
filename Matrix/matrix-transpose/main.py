from Matrix.Matrix import Matrix
from Parser.MatrixLexer import make_lexer
from Parser.MatrixParser import make_parser

# find matrix by ID name
def find_matrix_by_id(id: str, _data: dict):
    for item in _data:
        if item.name == id:
            return item
    print(f'No se ha podido encontrar la matriz con ID {id} en la Pila de matrices')
    return None


# READ DATA from file, file must be in local path
def read_file(file_path: str) -> str:
    try:
        with open(file_path, "r") as file:
            return file.read()
    except Exception as e:
        print(f'No se ha encontrado el archivo {file_path},\n error: {e}')


# execute the app, creates lexer, parser, handle data and print steps
def gen_transpose(matrix: Matrix):
    print(f"-------- Transpuesta de matriz {matrix.name} --------\n")
    print(matrix)
    matrix.transpose()
    print(matrix)

# main function
def main():
    data = f"{read_file('matrix.txt')}"
    lexer = make_lexer()
    parser = make_parser()
    result = parser.parse(data, lexer=lexer)
    # execute all operations using result["matrices"] and result["operations"]
    for operation in result["operations"]:
        gen_transpose(find_matrix_by_id(operation, result["matrices"]))




if __name__ == '__main__':
    main()



import numpy as np
from Matrix.ArithSolver import Operation, ArithSolver as Solver
from Utils.PrinterManager import PrinterManager as Printer

# THIS CLASS IF FOR TESTING, ENTRE MANUALLY THE MATRICES

def test_sub():
    matrix_a = np.array([[1, 2, 4], [3, 0, 4], [1, 1, -4]])
    matrix_b = np.array([[5, 6, 1], [7, 8, 8], [3, -2, 0]])
    solver = Solver()
    solver.solve_basic_operation(Operation.SUBTRACT, matrix_a, matrix_b)


def test_sum():
    # Example usage:
    matrix_a = np.array([[1, 2, 4], [3, 0, 4], [1, 1, -4]])
    matrix_b = np.array([[5, 6, 1], [7, 8, 8], [3, -2, 0]])
    solver = Solver()
    solver.solve_basic_operation(Operation.ADD, matrix_a, matrix_b)


def test_mult():
    matrix_a = np.array([[1, 2], [3, 0], [1, 1]])
    matrix_b = np.array([[7, 8, 8], [3, -2, 0]])
    solver = Solver()
    solver.solve_basic_operation(Operation.MULTIPLY, matrix_a, matrix_b)


def test_division():
    # Define matrices A and B
    matrix_a = np.array([[1, 2, 4, 1], [3, 0, 0, 4], [1, 1, -2, 6], [9, -2, -1, 8]])
    matrix_b = np.array([[1, 2, 4, 1], [3, 3, 1, 9], [1, 1, 1, 1], [0, -11, 2, 1]])
    solver = Solver()
    solver.solve_basic_operation(Operation.DIVIDE, matrix_a, matrix_b)


def main():
    try:
        # test_sum()
        test_sub()
        # test_mult()
        # test_division()
    except Exception as e:
        Printer.custom_print(f'No se ha podido recuperar el sistema del error ocurrido\nError {e}')


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()
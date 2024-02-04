import numpy as np
from Matrix.ArithSolver import Operation, ArithSolver as Solver
from Utils.PrinterManager import PrinterManager as Printer


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


def main():
    try:
        # test_sum()
        test_sub()
    except Exception as e:
        Printer.custom_print(f'Error unrecoverable at program {e}')


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/

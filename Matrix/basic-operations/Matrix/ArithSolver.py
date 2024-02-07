import numpy as np
from enum import Enum
from Utils.PrinterManager import PrinterManager as Printer


class Operation(Enum):
    ADD = '+'
    SUBTRACT = '-'
    MULTIPLY = '*'
    DIVIDE = '/'

    def __str__(self):
        return self.value


class ArithSolver:

    @staticmethod
    def _validate_matrices(operation: Operation, matrix_a: np.ndarray, matrix_b: np.ndarray):
        try:
            # Validate dimensions for addition and subtraction
            if operation in [Operation.ADD, Operation.SUBTRACT] and matrix_a.shape != matrix_b.shape:
                return [False, f'Matrices must be nxm both, shapes invalid for {matrix_a.shape}, {matrix_b.shape}']

            # Validate dimensions for multiplication
            if operation == Operation.MULTIPLY and matrix_a.shape[1] != matrix_b.shape[0]:
                return [False,
                        f'Matrices missmatch, matrix a has {matrix_a.shape[1]} columns and matrix b has {matrix_b.shape[0]} rows. Must be equals']

            # Validate dimensions for division (inverse exists only for square matrices)
            if operation == Operation.DIVIDE and matrix_a.shape[0] != matrix_a.shape[1] or matrix_b.shape[0] != matrix_b.shape[1]:
                return [False, 'Cannot perform division. Matrices must be square.']

            return [True, 'OK']
        except Exception as e:
            return [False, f'Unexpected problem encountered, error {e}']

    def solve_basic_operation(self, operation: Operation, matrix_a: np.ndarray, matrix_b: np.ndarray):
        # Validate matrices
        validation_result = self._validate_matrices(operation, matrix_a, matrix_b)
        if not validation_result[0]:
            Printer.custom_print(f"Invalid matrices for the operation. {validation_result[1]}")
            return

        # Perform matrix operation
        if operation == Operation.ADD:
            self._sum_matrices(matrix_a, matrix_b)
        elif operation == Operation.SUBTRACT:
            self._sub_matrices(matrix_a, matrix_b)
        elif operation == Operation.MULTIPLY:
            self._multiply_matrices(matrix_a, matrix_b)
        elif operation == Operation.DIVIDE:
            self._divide_matrices(matrix_a, matrix_b)
        else:
            Printer.custom_print('Invalid operation')
            return

    @staticmethod
    def _sum_matrices(matrix_a: np.ndarray, matrix_b: np.ndarray):
        try:
            # PRINT INIT
            Printer.custom_print("--------- ADDITION ---------")
            Printer.custom_print(f"A = \n{matrix_a}\nB =\n{matrix_b}")

            # Initialize a matrix for the result
            result = np.zeros(matrix_a.shape)

            # Perform matrix addition
            for i in range(matrix_a.shape[0]):
                for j in range(matrix_a.shape[1]):
                    result[i, j] = matrix_a[i, j] + matrix_b[i, j]
                    # Print each step
                    Printer.custom_print(f"A[{i},{j}] + B[{i},{j}] = {matrix_a[i, j]} + {matrix_b[i, j]} = {result[i, j]}")

            Printer.custom_print("RESULT: ")
            Printer.custom_print_array(result)
        except Exception as e:
            Printer.custom_print(f'Unable to execute values: {e}')

    @staticmethod
    def _sub_matrices(matrix_a: np.ndarray, matrix_b: np.ndarray):
        try:
            # PRINT INIT
            Printer.custom_print("--------- SUBTRACTION ---------")
            Printer.custom_print(f"A = \n{matrix_a}\nB =\n{matrix_b}")

            # Initialize a matrix for the result
            result = np.zeros(matrix_a.shape)

            # Perform matrix addition
            for i in range(matrix_a.shape[0]):
                for j in range(matrix_a.shape[1]):
                    result[i, j] = matrix_a[i, j] - matrix_b[i, j]
                    # Print each step
                    Printer.custom_print(
                        f"A[{i},{j}] + B[{i},{j}] = {matrix_a[i, j]} - {matrix_b[i, j]} = {result[i, j]}")

            Printer.custom_print("RESULT: ")
            Printer.custom_print_array(result)
        except Exception as e:
            Printer.custom_print(f'Unable to execute values: {e}')

    @staticmethod
    def _multiply_matrices(matrix_a: np.ndarray, matrix_b: np.ndarray):
        try:
            # PRINT INIT
            Printer.custom_print("--------- MULTIPLICATION ---------")
            Printer.custom_print(f"A = \n{matrix_a}\nB =\n{matrix_b}")

            # Initialize a matrix for the result
            result = np.zeros((matrix_a.shape[0], matrix_b.shape[1]))

            # Perform matrix multiplication
            for i in range(matrix_a.shape[0]):
                for j in range(matrix_b.shape[1]):
                    for k in range(matrix_a.shape[1]):
                        result[i, j] += matrix_a[i, k] * matrix_b[k, j]
                        # Print each step
                        Printer.custom_print(f"R[{i},{j}] += A[{i},{k}] * B[{k},{j}] = "
                                             f"{result[i, j]} + {matrix_a[i, k]} * {matrix_b[k, j]} = {result[i, j]}")
            # Print the final result
            Printer.custom_print("Final Result:")
            Printer.custom_print_array(result)
        except Exception as e:
            Printer.custom_print(f'Unable to execute multiplication, {e}')

    def _divide_matrices(self, matrix_a: np.ndarray, matrix_b: np.ndarray):
        Printer.custom_print("--------- DIVISION ---------")
        Printer.custom_print(f"A = \n{matrix_a}\nB =\n{matrix_b}")
        try:
            # step 1: inverse
            inverse_matrix_b = np.linalg.inv(matrix_b)
            # Print the inverse matrix
            Printer.custom_print("Inverse of Matrix B:")
            Printer.custom_print(inverse_matrix_b)
            # Step 2: Multiply matrix_a by the inverse of matrix_b
            self._multiply_matrices(matrix_a, inverse_matrix_b)

        except np.linalg.LinAlgError:
            Printer.custom_print("Matrix B is not invertible. Cannot perform division.")
            return

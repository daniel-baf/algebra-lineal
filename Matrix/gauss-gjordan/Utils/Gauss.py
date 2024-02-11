# create an enum to allow a type, named Operation
from enum import Enum
from Utils.Matrix import Matrix
from Utils.PrinterManager import PrinterManager as Printer


class Operation(Enum):
    GAUSS = "GAUSS"
    GAUSS_JORDAN = "GAUSS_JORDAN"

    def __str__(self):
        return self.value


class GaussTokenList:
    def __init__(self, operations: list, type_operation: Operation):
        self.operations = operations
        self.type = type_operation

    def __str__(self):
        return f"{self.type} {self.operations}"

    @staticmethod
    def _find_matrix(matrix_name: str, matrices: list):
        for matrix in matrices:
            if matrix.name == matrix_name:
                return matrix
        return None

    @staticmethod
    def _print_matrices(matrices: list):
        print("PILA DE MATRICES")
        for item in matrices:
            Printer.custom_print(item)
        return ""

    def solve(self, matrices: list):
        try:
            solver = GaussSolver()
            self._print_matrices(matrices=matrices)
            Printer.custom_print(f"----- RESOLVIENDO {self.type} -----")
            for matrix_name in self.operations:
                matrix = self._find_matrix(matrix_name, matrices)
                if matrix is not None:
                    solver.solve(self.type, matrix)
                else:
                    Printer.custom_print(f"Matriz {matrix_name} no encontrada en la pila de matrices")
        except Exception as e:
            Printer.custom_print(f"Error intentando resolver matrices: {e}")


class GaussSolver:
    def __init__(self):
        pass

    #  solve a matrix using substitution to print a step by step
    @staticmethod
    def _solve_substitution(s_matrix):
        # now we have the Gauss matrix, print a step by step substitution with the generated matrix
        Printer.custom_print("Sustitucion hacia atras")
        n = s_matrix.shape[0]
        # create an array of size n and fill it with multiple x, but add a number x1, x2... till xn
        variables = [f"x{i + 1}" for i in range(n)]
        solutions = [0] * n

        for i in range(n - 1, -1, -1):
            solutions[i] = s_matrix[i, -1]
            for j in range(i + 1, n):
                # print the step by step
                Printer.custom_print(f"x{i} = {solutions[i]} - {s_matrix[i, j]} * {solutions[j]}")
                solutions[i] -= s_matrix[i, j] * solutions[j]
            Printer.custom_print(f"x{i} = {solutions[i]} / {s_matrix[i, i]}")
            solutions[i] /= s_matrix[i, i]
            Printer.custom_print(f"\t  R: {variables[i]} = {solutions[i]}")
        return {"variables": variables, "results": solutions}

    # print the solutions of the matrix
    @staticmethod
    def _print_solutions(solutions: dict):
        Printer.custom_print("\tSOLUCIONES")
        for i in range(len(solutions['variables'])):
            Printer.custom_print(f"\t  {solutions['variables'][i]} = {solutions['results'][i]}")

    def _solve_gauss(self, matrix: Matrix, print_results: bool = True):
        # solve a matrix using gauss method and print a step by step
        # create a copy of matrix, so I can keep the original
        s_matrix = matrix.matrix.copy()
        for i in range(s_matrix.shape[0]):
            # iterate over rows
            for j in range(i + 1, s_matrix.shape[0]):
                # get the factor to multiply the row
                factor = s_matrix[j, i] / s_matrix[i, i]
                # iterate over columns
                for k in range(s_matrix.shape[1]):
                    # multiply the row by the factor and subtract from the row
                    s_matrix[j, k] = s_matrix[j, k] - factor * s_matrix[i, k]
                Printer.custom_print(f"\t  PASO {i + 1}: FILA {j + 1} - FILA {i + 1} * {factor} = FILA {j + 1}")
            if i < s_matrix.shape[0] - 1:
                Printer.custom_print_array(s_matrix)

        # check if method used to solve is gauss or gauss jordan
        if print_results:
            self._print_solutions(self._solve_substitution(s_matrix))
        return s_matrix

    @staticmethod
    def _solve_gauss_jordan(matrix: Matrix):
        s_matrix = matrix.matrix.copy()
        rows, cols = s_matrix.shape
        Printer.custom_print("MATRIZ INICIAL:")
        Printer.custom_print_array(s_matrix)
        for i in range(rows):
            # Partial pivoting
            max_row = i
            for k in range(i + 1, rows):
                if abs(s_matrix[k][i]) > abs(s_matrix[max_row][i]):
                    max_row = k
            # Swap rows
            temp = s_matrix[i].copy()
            s_matrix[i] = s_matrix[max_row]
            s_matrix[max_row] = temp

            Printer.custom_print(f"Pivote final: Cambio de filas F({i + 1} con F{max_row + 1})")
            Printer.custom_print_array(s_matrix)

            # Make the diagonal elements 1
            divisor = s_matrix[i][i]
            for j in range(cols):
                s_matrix[i][j] /= divisor

            Printer.custom_print(f"Reduciendo las lineas con la diagonal ({i + 1}, {i + 1})")
            Printer.custom_print_array(s_matrix)

            # Make other elements in the column zero
            for k in range(rows):
                if k != i:
                    factor = s_matrix[k][i]
                    for j in range(cols):
                        s_matrix[k][j] -= factor * s_matrix[i][j]

            Printer.custom_print(f"Elementos de {i + 1} cero:")
            Printer.custom_print_array(s_matrix)

    def solve(self, operation: Operation, matrix: Matrix):
        Printer.custom_print(f"{operation} para matriz {matrix.name}")
        if operation == Operation.GAUSS:
            self._solve_gauss(matrix)
        elif operation == Operation.GAUSS_JORDAN:
            self._solve_gauss_jordan(matrix)
        else:
            Printer.custom_print(f"La operacion {operation} no es valida para ejecutar en la matriz {matrix}")

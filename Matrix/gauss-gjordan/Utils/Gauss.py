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
    def __init__(self, operations: list, type: Operation):
        self.operations = operations
        self.type = type
    
    def __str__(self):
        return f"{self.type} {self.operations}"


    def _find_matrix(self, matrix_name: str, matrices: list):
        for matrix in matrices:
            if matrix.name == matrix_name:
                return matrix
        return None

    def _print_matrices(self, matrices: list):
        print("MATRICES POOL")
        for item in matrices:
            Printer.custom_print(item)
        return ""

    def solve(self, matrices: list):
        try:
            solver = GaussSolver()
            self._print_matrices(matrices=matrices)
            Printer.custom_print(f"INIT OF SOLVING {self.type}")
            for matrix_name in self.operations:
                matrix = self._find_matrix(matrix_name, matrices)
                if matrix is not None:
                    solver.solve(self.type, matrix)
                else: Printer.custom_print(f"Matrix {matrix_name} not found")
        except Exception as e:
            Printer.custom_print(f"Error trying to solve matrices {e}")


class GaussSolver:
    def __init__(self):
        pass

    def solve(self, operation: Operation, matrix: str):
        Printer.custom_print(f"INIT OF OPERATION {operation} for matrix {matrix.name}")
        if operation == Operation.GAUSS:
            self._solve_gauss(matrix)
        elif operation == Operation.GAUSS_JORDAN:
            # self._solve_gauss_jordan(matrix)
            pass
        else:
            Printer.custom_print(f"No valid operation {operation} to execute for matrix {matrix}")

    
    def _solve_gauss(self, matrix: Matrix):
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
                Printer.custom_print(f"\t  STEP {i + 1}: ROW {j + 1} - ROW {i + 1} * {factor} = ROW {j + 1}")
            Printer.custom_print(s_matrix)
        self._print_solutions(self._solve_substitution(s_matrix))
    

    def _solve_substitution(self, s_matrix):
                # now we have the Gauss matrix, print a step by step substitution with the generated matrix
        Printer.custom_print("Substitution backwards")
        n = s_matrix.shape[0]
        # create an array of size n and fill it with multiple x, but add a number x1, x2... till xn
        variables = [f"x{i + 1}" for i in range(n)]        
        solutions = [0] * n

        for i in range(n - 1, -1, -1):
            solutions[i] = s_matrix[i, -1]
            for j in range(i + 1, n):
                # print the step by step
                Printer.custom_print(f"x{i} = {solutions[i]} - {s_matrix[i,j]} * {solutions[j]}")
                solutions[i] -= s_matrix[i, j] * solutions[j]
            Printer.custom_print(f"x{i} = {solutions[i]} / {s_matrix[i,i]}")
            solutions[i] /= s_matrix[i, i]
            Printer.custom_print(f"\t  R: {variables[i]} = {solutions[i]}")
        return {"variables": variables, "results": solutions}


    def _print_solutions(self, solutions: dict):
        Printer.custom_print("\tSOLUCIONES")
        for i in range(len(solutions['variables'])):
            Printer.custom_print(f"\t  {solutions['variables'][i]} = {solutions['results'][i]}")

    def _solve_gauss_jordan(self, matrix: Matrix):
        pass
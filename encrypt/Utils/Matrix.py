import numpy as np


class Matrix:

    def __init__(self, name: str, matrix: np.ndarray = None):
        self.name = name
        self.matrix = matrix

    def __str__(self):
        return f"Matrix {self.name} = \n{self.matrix}"

    def determinant(self):
        if self.matrix.shape[0] != self.matrix.shape[1]:
            return 0

        if self.matrix.shape[0] == 1:
            return self.matrix[0, 0]

        if self.matrix.shape[0] == 2:
            return self.matrix[0, 0] * self.matrix[1, 1] - self.matrix[0, 1] * self.matrix[1, 0]

        det = 0
        for j in range(self.matrix.shape[1]):
            sign = (-1) ** j
            minor = np.delete(np.delete(self.matrix, 0, axis=0), j, axis=1)
            det += sign * self.matrix[0, j] * Matrix(self.name,
                                                     minor).determinant()  # Calling determinant without argument

        return det

    def inverse(self):
        n = self.matrix.shape[0]
        augmented_matrix = np.hstack((self.matrix, np.identity(n)))

        # Gauss-Jordan elimination
        for i in range(n):
            if augmented_matrix[i, i] == 0:
                for j in range(i + 1, n):
                    if augmented_matrix[j, i] != 0:
                        augmented_matrix[[i, j]] = augmented_matrix[[j, i]]
                        break
            pivot = augmented_matrix[i, i]
            augmented_matrix[i] /= pivot
            for j in range(n):
                if j != i:
                    factor = augmented_matrix[j, i]
                    augmented_matrix[j] -= factor * augmented_matrix[i]

        inverse_matrix = augmented_matrix[:, n:]
        return inverse_matrix

    def transpose(self):
        if self.matrix is None:
            return None
        rows, cols = self.matrix.shape
        transposed_matrix = np.zeros((cols, rows))  # Create a new matrix with swapped dimensions
        for i in range(rows):
            for j in range(cols):
                transposed_matrix[j, i] = self.matrix[i, j]  # Swap elements
        return Matrix(self.name, transposed_matrix)  # Return the transposed matrix


class MatrixManager:

    # multiply two matrices type Matrix
    @staticmethod
    def matrix_multiply(matrix_a: Matrix, matrix_b: Matrix) -> Matrix | None:
        if matrix_a.matrix.shape[1] != matrix_b.matrix.shape[0]:
            print("Error: Number of columns in Matrix A must be equal to number of rows in Matrix B")
            return None

        result = np.zeros((matrix_a.matrix.shape[0], matrix_b.matrix.shape[1]))

        for i in range(matrix_a.matrix.shape[0]):
            for j in range(matrix_b.matrix.shape[1]):
                for k in range(matrix_a.matrix.shape[1]):
                    result[i, j] += matrix_a.matrix[i, k] * matrix_b.matrix[k, j]

        return Matrix("Result", result)

import numpy as np

# Class used to manage oeprations, save name and nparray for matrix
class Matrix:

    def __init__(self, name: str, matrix: np.ndarray):
        self.name = name
        self.matrix = matrix
        
    def __str__(self):
        return f"Matrix {self.name}:\n{self.matrix}"
    

    # calculates the transpose of a matrix
    def transpose(self):
        rows = self.matrix.shape[0]
        cols = self.matrix.shape[1]
        transposed_matrix = np.zeros((cols, rows))

        # create a new matrix to make it transposed and print the step by step process
        for i in range(rows):
            for j in range(cols):
                print(f"Transpuesta[{j}][{i}] = matrix[{i}][{j}]")
                transposed_matrix[j][i] = self.matrix[i][j]

        self.matrix = transposed_matrix
        # check if current names contains ', if not, ad d it, otherwise, remote it
        if "'" not in self.name:
            self.name = f"{self.name}'"
        else:
            self.name = f"{self.name}"
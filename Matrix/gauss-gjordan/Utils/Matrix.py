import numpy as np


class Matrix:

    def __init__(self, name: str, matrix: np.ndarray):
        self.name = name
        self.matrix = matrix

    def __str__(self):
        return f"Matrix {self.name} = \n{self.matrix}"

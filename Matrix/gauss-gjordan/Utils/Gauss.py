# create an enum to allow a type, named Operation
from enum import Enum

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
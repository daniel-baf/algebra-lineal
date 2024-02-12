from Utils.PrinterManager import PrinterManager as Printer
import numpy as np
from Matrix.ArithSolver import Operation, ArithSolver as Solver

class DerivationNode:

    # parent_node, right_child, left_child must be DerivationNode type
    def __init__(self, data: np.ndarray | str, left_child=None, right_child=None, parent_node=None, ):
        self.data = data
        self.left_child = left_child
        self.right_child = right_child
        self.parent_node = parent_node

    def solve(self, root_node: object):
        pass
        try:
            # call recursively
            if self.left_child is not None and not isinstance(self.left_child.data, np.ndarray):
                self.solve(self.left_child)
            if self.right_child is not None and not isinstance(self.right_child.data, np.ndarray):
                self.solve(self.right_child)
            self.execute(root_node)
            return root_node
        except Exception as e:
            Printer.custom_print(f"ERROR EN EL ARBOL DE DERIVACIONES {e}")

    def execute(self, root_node: object):
        if self.data == "/":
            self.data = self.left_child.data  # data -> left data
            self.data = Solver().solve_basic_operation(Operation.DIVIDE, self.data, self.right_child.data)  # data -> left data / right data
            self.break_children()
        elif self.data == "*":
            self.data = self.left_child.data  # data -> left data
            self.data = Solver().solve_basic_operation(Operation.MULTIPLY, self.data, self.right_child.data)  # data -> left data * right data
            self.break_children()
        elif self.data == "-":
            self.data = self.left_child.data  # data -> left data
            self.data = Solver().solve_basic_operation(Operation.SUBTRACT, self.data, self.right_child.data)  # data -> left data - right data
            self.break_children()
        elif self.data == "+":
            self.data = self.left_child.data  # data -> left data
            self.data = Solver().solve_basic_operation(Operation.ADD, self.data, self.right_child.data)  # data -> left data + right data
            self.break_children()
        elif self.data == "()":
            self.data = self.left_child.data  # data -> left data
            self.break_children()
        # Printer.custom_print(f"{root_node}")
        return root_node

    def break_children(self):
        self.left_child = self.right_child = None  # delete child pointeres

    @classmethod
    def from_other_object(cls, other_object):
        # Extract relevant attributes from the generic Object
        data = other_object.data
        parent_node = other_object.parent_node
        left_child = other_object.left_child
        right_child = other_object.right_child
        return cls(data, parent_node, left_child, right_child)

    def __str__(self):
        return self.get_printable()

    def get_printable(self):
        if(type(self.data) == np.ndarray):
            return f"{self.data if self.data is not None else ''}"
        elif (type(self.data) == str):
            if self.data == "()":
                return f"({self.left_child.get_printable() if self.left_child is not None else ''})"
            elif self.data == "+" or "-" or "*" or "/":
                return f"{self.left_child.get_printable() if self.left_child is not None else ''}{self.data}{self.right_child.get_printable() if self.right_child is not None else ''}"
            else:
                return f"{self.data if self.data is not None else ''}"
        return ''
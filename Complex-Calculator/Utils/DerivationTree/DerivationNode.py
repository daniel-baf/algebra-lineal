from Utils.ComplexNumber import ComplexNumber


class DerivationNode:

    # parent_node, right_child, left_child must be DerivationNode type
    def __init__(self, data: ComplexNumber | str, left_child=None, right_child=None, parent_node=None, ):
        self.data = data
        self.left_child = left_child
        self.right_child = right_child
        self.parent_node = parent_node

    def solve(self, root_node: object):
        try:
            # call recursively
            if self.left_child is not None and not isinstance(self.left_child.data, ComplexNumber):
                self.left_child.solve(root_node)
            if self.right_child is not None and not isinstance(self.right_child.data, ComplexNumber):
                self.right_child.solve(root_node)
            self.execute(root_node)
        except Exception as e:
            print(f"ERROR at derivation node {e}")

    def execute(self, root_node: object):
        if self.data == "/":
            self.data = self.left_child.data  # data -> left data
            self.data.div(self.right_child.data)  # data -> left data / right data
            self.break_children()
        elif self.data == "*":
            self.data = self.left_child.data  # data -> left data
            self.data.mult(self.right_child.data)  # data -> left data / right data
            self.break_children()
        elif self.data == "-":
            self.data = self.left_child.data  # data -> left data
            self.data.sub(self.right_child.data)  # data -> left data / right data
            self.break_children()
        elif self.data == "+":
            self.data = self.left_child.data  # data -> left data
            self.data.add(self.right_child.data)  # data -> left data / right data
            self.break_children()
        elif self.data == "()":
            self.data = self.left_child.data  # data -> left data
            self.break_children()
        elif self.data == "||":
            self.data = self.left_child.data  # data -> left data
            self.data.complement()
            self.break_children()
        print(f"{root_node}")

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
        if self.data == "()":
            return f"({self.left_child.get_printable() if self.left_child is not None else ''})"
        elif self.data == "||":
            return f"||{self.left_child.get_printable() if self.left_child is not None else ''}||"
        elif self.data == "+" or "-" or "*" or "/":
            return f"{self.left_child.get_printable() if self.left_child is not None else ''}{self.data}{self.right_child.get_printable() if self.right_child is not None else ''}"
        else:
            return f"{self.data if self.data is not None else ''}"

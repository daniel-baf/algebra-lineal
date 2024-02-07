class ComplexNumber:
    def __init__(self, real_value, imaginary_value):
        self.real_value = real_value
        self.imaginary_value = imaginary_value

    def add(self, other):
        complex_a = complex(self.real_value, self.imaginary_value)
        complex_b = complex(other.real_value, other.imaginary_value)
        complex_result = complex_a + complex_b
        self.save(complex_result)

    def sub(self, other):
        complex_a = complex(self.real_value, self.imaginary_value)
        complex_b = complex(other.real_value, other.imaginary_value)
        complex_result = complex_a - complex_b
        self.save(complex_result)

    def mult(self, other):
        complex_a = complex(self.real_value, self.imaginary_value)
        complex_b = complex(other.real_value, other.imaginary_value)
        complex_result = complex_a * complex_b
        self.save(complex_result)

    def div(self, other):
        complex_a = complex(self.real_value, self.imaginary_value)
        complex_b = complex(other.real_value, other.imaginary_value)
        complex_result = complex_a / complex_b
        self.save(complex_result)

    def complement(self):
        self.imaginary_value = self.imaginary_value * -1

    def save(self, complex_result: complex):
        self.real_value = complex_result.real
        self.imaginary_value = complex_result.imag

    @classmethod
    def from_other_object(cls, other_object):
        # Extract relevant attributes from the generic Object
        real_value = other_object.real_value
        imaginary_value = other_object.imaginary_value
        return cls(real_value, imaginary_value)

    def __str__(self):
        return f"{round(self.real_value,2) if self.real_value != 0 else ''}{'+'if self.imaginary_value >0 and self.real_value != 0 else ''}{f'{round(self.imaginary_value,2)}i' if self.imaginary_value != 0 else ''}"

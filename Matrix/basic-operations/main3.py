# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
import numpy as np


def print_hi(name):
    # Define your matrices A and B
    A = np.array([[1, 2], [3, 4]])
    B = np.array([[5, 6], [7, 8]])

    # Check if matrices A and B have the same dimensions
    if A.shape == B.shape:
        # Initialize a matrix for the result
        result = np.zeros(A.shape)

        # Perform matrix addition
        for i in range(A.shape[0]):
            for j in range(A.shape[1]):
                result[i, j] = A[i, j] + B[i, j]
                # Print each step
                print(f"A[{i},{j}] + B[{i},{j}] = {A[i, j]} + {B[i, j]} = {result[i, j]}")

        # Print the final result
        print("Result:")
        print(result)
    else:
        print("Matrices A and B have different dimensions, cannot perform addition.")


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    print_hi('PyCharm')

# See PyCharm help at https://www.jetbrains.com/help/pycharm/

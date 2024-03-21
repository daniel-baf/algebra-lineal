import numpy as np
import math


# function to get the KEY
def get_matrix_key():
    return np.array([[1, 4, 2], [13, 24, 1], [2, 41, 4]])


# Check if a matrix has an inverse
def has_inverse(matrix):
    return np.linalg.det(matrix) != 0


# get inverse of a matrix
def get_inverse(matrix):
    return np.linalg.inv(matrix)


# multiply two matrices
def multiply_matrices(matrix1, matrix2):
    return np.dot(matrix1, matrix2)


# Step 1: Create a dictionary to map letters to numbers
def create_letter_to_number_mapping():
    letter_to_number = {}
    for i, char in enumerate("abcdefghijklmnopqrstuvwxyz"):
        letter_to_number[char] = i + 1
    for i, char in enumerate("ABCDEFGHIJKLMNOPQRSTUVWXYZ"):
        letter_to_number[char] = i + 1 + 26
    # space
    letter_to_number[' '] = 53
    return letter_to_number


# Step 2, convert input text to an array of numbers
def text_to_numbers(text, letter_to_number):
    return [letter_to_number[char] for char in text]


# Step 3: Cast the vector to a matrix of order key.cols * n
def vector_to_matrix(vector, key):
    num_rows = key.shape[1]
    num_cols = math.ceil(len(vector) / num_rows)  # Adjusted to always round up
    non_filled_data = abs(len(vector) - (num_rows * num_cols))
    for _ in range(non_filled_data):  # make matrix autocompleted with empty values
        vector.append(0)
    return np.array(vector).reshape(num_cols, num_rows).T


def main():
    try:
        # variables
        text = "Daniel Eduardo Bautista Fuentes"
        # get key
        matrix_key = get_matrix_key()
        if not has_inverse(matrix_key):  # check if matrix has inverse
            print('la llave no tiene iversa')
            return
        # STEP 1: create matrix keys
        letter_to_numbers = create_letter_to_number_mapping()
        # STEP 2: convert input to text
        numbers = text_to_numbers(text, letter_to_numbers)
        # Step 3
        matrix = vector_to_matrix(numbers, matrix_key)
        # calculate inverse of matrix
        inverse_key = get_inverse(matrix_key)

        encrypted_matrix = multiply_matrices(matrix_key, matrix)
        decripted_matrix = multiply_matrices(inverse_key, encrypted_matrix)

        # PRINTS
        print('TEXTO:\t', text)
        print(numbers)
        print('MATRIZ:\t\n', matrix)
        print('INVERSA CLAVE:\n', inverse_key)
        print('CLAVE ENCRIPTADA\n', encrypted_matrix)
        print('CLAVE DESECNRIPTADA\n', decripted_matrix)
    except Exception as e:
        print(e)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/

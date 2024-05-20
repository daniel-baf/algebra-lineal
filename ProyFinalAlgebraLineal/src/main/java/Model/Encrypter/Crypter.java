package Model.Encrypter;

import Model.Matrix.Matrix;
import Model.Utils.CustomLogger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jefe_mayoneso
 */
public class Crypter {

    private final Matrix key;
    private static Crypter instance;
    private final Map<Character, Integer> mappingStrings;

    private Crypter() {
        double[][] data = {{1, -3, 2}, {0, -4, 3}, {1, 4, 21}};
        this.key = new Matrix("KEY", data);
        this.mappingStrings = this.createLetterToNumberMapping();
    }

    public static Crypter getInstance() {
        if (instance == null) {
            instance = new Crypter();
        }
        return instance;
    }

    /**
     * Step 1: Create a map to map letters to numbers
     *
     * @return mapping for encrypt
     */
    private Map<Character, Integer> createLetterToNumberMapping() {
        Map<Character, Integer> letterToNumber = new HashMap<>();
        letterToNumber.put(' ', 0); // space
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚabcdefghijklmnopqrstuvwxyzáéíóú";
        for (int i = 0; i < upperCaseLetters.length(); i++) {
            letterToNumber.put(upperCaseLetters.charAt(i), i + 1);
        }
        return letterToNumber;
    }

    /**
     * Step 2: Convert input text to an array of numbers
     *
     * @param text text to cast
     * @return vector of numbers from string
     */
    private int[] textToNumbers(String text) {
        int[] textNumbers = new int[text.length()];
        for (int iteration = 0; iteration < text.length(); iteration++) {
            char currentCharacter = text.charAt(iteration);
            textNumbers[iteration] = this.mappingStrings.getOrDefault(currentCharacter, 0);
        }
        return textNumbers;
    }

    /**
     * Step 3: Cast the vector to a matrix of order key.cols * n
     *
     * @param vector vector to cast
     * @return vector as matrix
     */
    private Matrix vectorToMatrix(int[] vector) {
        int numRows = this.key.getMatrix().length;
        int numCols = (int) Math.ceil((double) vector.length / numRows); // Adjusted to always round up
        double[][] matrix = reshapeVector(vector, numRows, numCols);
        return new Matrix("TEXT", matrix);
    }

    // Reshape a plain vector to a matrix

    /**
     * Reshape the
     *
     * @param vector  original vector
     * @param numRows no. rows
     * @param numCols no. columns
     * @return vector as matrix of doubles
     */
    private double[][] reshapeVector(int[] vector, int numRows, int numCols) {
        double[][] matrix = new double[numRows][numCols];
        int index = 0;
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                if (index < vector.length) {
                    matrix[row][col] = vector[index++];
                } else {
                    matrix[row][col] = 0; // Fill with zeros if vector is smaller than matrix
                }
            }
        }
        return matrix;
    }

    /**
     * Encrypt text using the provided key matrix
     *
     * @param text    text en encrypt
     * @param verbose true or false to step by step
     * @return encrypted matrix
     */
    public Matrix encrypt(String text, boolean verbose) {
        CustomLogger.getInstance().addLog("\tCreate mapping : " + this.mappingStrings.toString(), verbose);

        if (!this.key.hasInverse()) {
            throw new IllegalArgumentException("Matrix key has no inverse");
        }
        // generate vector from string
        int[] textNumbers = this.textToNumbers(text);
        CustomLogger.getInstance().addLog("Cast text to numbers: " + Arrays.toString(textNumbers), verbose);
        // cast vector to matrix
        Matrix vectorMatrix = this.vectorToMatrix(textNumbers);
        CustomLogger.getInstance().addLog("Cast numbers vector to matrix:\n " + vectorMatrix, verbose);
        // multiply key * matrix
        vectorMatrix = this.key.multiply(vectorMatrix, verbose);
        CustomLogger.getInstance().addLog("Multiply result:\n " + vectorMatrix.toString(), verbose);
        return vectorMatrix;
    }

    /**
     * Step 4: Convert numbers back to text using the letter-to-number mapping
     *
     * @param number number to cast to char
     * @return number as char
     */
    public char numberToChar(int number) {
        for (Map.Entry<Character, Integer> entry : this.mappingStrings.entrySet()) {
            if (entry.getValue() == number) {
                return entry.getKey();
            }
        }
        return '\0'; // Return null character if number is not found
    }

    public String numbersToString(int[] numbers) {
        StringBuilder builder = new StringBuilder();
        for (int number : numbers) {
            char character = numberToChar(number);
            if (character != '\0') {
                builder.append(character);
            }
        }
        return builder.toString().trim();
    }

    /**
     * Cast a matrix to a vector of integers
     *
     * @param matrix matrix to cast
     * @return matrix as vector
     */
    public int[] matrixToIntegerArray(double[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        int[] result = new int[numRows * numCols];
        int index = 0;
        for (int currentColumn = 0; currentColumn < numCols; currentColumn++) {
            for (double[] doubles : matrix) {
                result[index++] = (int) doubles[currentColumn];
            }
        }
        return result;
    }

    public String decrypt(int[] numbersTextDecrypted, boolean verbose) {
        Matrix transactionMatrix = this.vectorToMatrix(numbersTextDecrypted);
        return this.decrypt(transactionMatrix, verbose);
    }

    /**
     * Decrypt the encrypted matrix using the provided key matrix
     *
     * @param matrix  matrtix to check
     * @param verbose true or false step by step
     * @return decrypted value
     */
    public String decrypt(Matrix matrix, boolean verbose) {
        try {
            if (!this.key.hasInverse()) {
                throw new IllegalArgumentException("Matrix has no inverse");
            }
            // get inerse
            CustomLogger.getInstance().addLog("\n\tCalculate inverse of key", verbose);
            Matrix inverseKey = this.key.inverse(verbose);
            CustomLogger.getInstance().addLog("\n\tKEY INVERSE\n" + inverseKey.toString(), verbose);
            // multiply inverse * encrypted matrix
            CustomLogger.getInstance().addLog("\n\tMultiply inverse key * matrix", verbose);
            Matrix resultMatrix = inverseKey.multiply(matrix, verbose);
            CustomLogger.getInstance().addLog("\nRESULT MULTIPLY\n" + resultMatrix.toString(), verbose);
            // cast result matrix to vector
            int[] numbersTextDecrypted = this.matrixToIntegerArray(resultMatrix.getMatrix());
            CustomLogger.getInstance().addLog("\tMATRIX RESULT TO VECTOR: " + Arrays.toString(numbersTextDecrypted), verbose);
            // cast vector to string final
            String decryptedText = this.numbersToString(numbersTextDecrypted);
            CustomLogger.getInstance().addLog("\tVector as string: " + decryptedText, verbose);
            return decryptedText;
        } catch (IllegalArgumentException e) {
            CustomLogger.getInstance().addLog("Cannot encrypt check values input" + e.getMessage(), verbose);
            return null;
        }
    }

}

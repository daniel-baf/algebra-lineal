package Model.Compiler;

import Model.Matrix.Matrix;
import Model.Matrix.MatrixEnum;
import Model.Utils.CustomLogger;

import java.util.ArrayList;
import java.util.Objects;

public class ParserUtils {

    public static ParserUtils instance;

    public ParserUtils() {
    }

    public static ParserUtils getInstance() {
        if (instance == null) {
            instance = new ParserUtils();
        }
        return instance;
    }

    /**
     * Centralize creation of matrix
     * @param identifier
     * @param tmpMatrix
     * @return
     */
    public Matrix configureFinalMatrix(String identifier, Matrix tmpMatrix) {
        tmpMatrix.setName(identifier);
        return tmpMatrix;
    }

    /**
     * Concatenate two matrices to create a single one, new matrix has the size of parent cols and the height of parent cols + child rows
     *
     * @param parentMatrix main matrix to cpy
     * @param newRowData         matrix to merge bellow main matrix
     * @return the new matrix
     */
    public Matrix concatMatrix(Matrix parentMatrix, ArrayList<Double> newRowData) {
        try {
            // recover data from input matrix or null -> shape = 0;
            Matrix tmpMatrix = Objects.isNull(parentMatrix) ? new Matrix("NODE") : (Matrix) parentMatrix.clone();
            // check compatible values
            if (Objects.isNull(newRowData) || newRowData.isEmpty()) {
                return tmpMatrix;
            }
            if (parentMatrix.shape(MatrixEnum.COLUMNS_SHAPE) != newRowData.size()) {
                CustomLogger.getInstance().addLog("Missmatch at row size to create matrix", true);
                return null;
            }
            tmpMatrix.setMatrix(addVectorToLastRow(tmpMatrix.getMatrix(), newRowData));
            return tmpMatrix;
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Unable to cast matrx " + e.getMessage(), true);
            return null;
        }
    }

    /**
     * Cast arraylist to a vector and append the new value to current matrix, create a new matrix.
     *
     * @param vector
     * @param data
     * @return
     */
    public static double[][] addVectorToLastRow(double[][] data, ArrayList<Double> vector) {
        // Create a new row with the size of the vector
        double[] newRow = new double[vector.size()];

        // Convert ArrayList to double[] array
        for (int i = 0; i < vector.size(); i++) {
            newRow[i] = vector.get(i);
        }

        // Resize the data array to accommodate the new row
        double[][] newData = new double[data.length + 1][];
        // Copy existing data to the newData array
        System.arraycopy(data, 0, newData, 0, data.length);
        // Add the new row to the last position
        newData[data.length] = newRow;

        return newData;
    }


    /**
     * Generic method to cast a vector to a matrix of 1xn
     *
     * @param vectorRow
     * @return
     */
    public Matrix generateMainMatrix(ArrayList<Double> vectorRow) {
        // cast to a new double matrix of rows 1
        Matrix resultMatrix = new Matrix("NODE");
        resultMatrix.setMatrix(this.addVectorToLastRow(new double[0][vectorRow.size()], vectorRow));
        return resultMatrix;
    }

    /**
     * Concat numbers into an arraylist, reuse this method on parser to save data from text
     *
     * @return the array
     */
    public ArrayList<Double> concatDoublesVector(ArrayList<Double> numbers, double number) {
        // check if numbers is null
        ArrayList<Double> numbersBackup = Objects.isNull(numbers) ? new ArrayList<>() : numbers;
        numbersBackup.add(number);
        return numbersBackup;
    }

    /**
     * Concat 2 arrays of numbers into a singleone
     *
     * @param numbers  original numbers
     * @param numbers2 new numbers
     * @return the concat array
     */
    public ArrayList<Double> concatDoublesVector(ArrayList<Double> numbers, ArrayList<Double> numbers2) {
        // check if numbers is null
        ArrayList<Double> numbersBackup = Objects.isNull(numbers) ? new ArrayList<>() : numbers;
        numbersBackup.addAll(numbers2);
        return numbersBackup;
    }

    /**
     * Remove comillas from string at the begginning and end of a string token
     * @param text the text without comillas
     * @return the new text
     */
    public String removeComillasToString(String text) {
        // check length of String
        if(text.length() <= 2) {
            return text;
        }
        if(text.substring(0,1) == "\"" && text.substring(text.length() -1, text.length()) == "\"") {
            return text.substring(1, text.length() - 1);
        }
        return text;
    }
}

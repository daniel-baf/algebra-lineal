package Model.Matrix;

import Model.Matrix.Utils.MatrixUtils;
import Model.Utils.CustomLogger;
import java.util.Arrays;

/**
 *
 * @author jefe_mayoneso
 */
public class Matrix implements Cloneable {

    // variables for operations
    private String name;
    private double[][] matrix;
    private final CustomLogger logger;
    private final MatrixUtils matrixUtils;

    // constructors
    private Matrix() {
        this(null);
    }

    public Matrix(String name) {
        this(name, null);
    }

    public Matrix(String name, double[][] matrix) {
        this.name = name;
        this.matrix = matrix;
        this.logger = CustomLogger.getInstance();
        this.matrixUtils = MatrixUtils.getInstance();
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public MatrixUtils getMatrixUtils() {
        return matrixUtils;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix: ").append(this.name).append("\n");
        if (this.matrix != null) {
            for (double[] row : this.matrix) {
                sb.append(Arrays.toString(row)).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Matrix clonedMatrix = (Matrix) super.clone();
        clonedMatrix.matrix = new double[this.matrix.length][];
        for (int i = 0; i < this.matrix.length; i++) {
            clonedMatrix.matrix[i] = Arrays.copyOf(this.matrix[i], this.matrix[i].length);
        }
        return clonedMatrix;
    }

    /**
     * Method to calculate the shape, COLUMNS OR ROWS, if invalid operation,
     * return -1
     *
     * @param MATRIX_AXIS the axis to calculate
     * @return the size of the matrix by the axis
     */
    public int shape(MatrixEnum MATRIX_AXIS) {
        if (this.matrix == null) { // check if matrix is null -> 0
            return 0;
        }
        // return the current size by MATRIX_AXIS
        return switch (MATRIX_AXIS) {
            case ROWS_SHAPE ->
                this.matrix.length;
            case COLUMNS_SHAPE ->
                this.matrix.length != 0 ? this.matrix[0].length : 0;
        };
    }

    /**
     * Return the transpose of current matrix
     *
     * @param verbose true | false to print step by step
     * @return transpose of current matrix
     */
    public Matrix transpose(boolean verbose) {
        int numRows = this.shape(MatrixEnum.ROWS_SHAPE);
        int numCols = this.shape(MatrixEnum.COLUMNS_SHAPE);
        // Create a new matrix to store the transposed matrix
        double[][] transposedMatrix = new double[numCols][numRows];
        this.logger.addLog("Transpose initial step", verbose);
        // Perform the transpose operation
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                transposedMatrix[j][i] = this.matrix[i][j];
                // Log each step of the transpose operation
                String logMessage = String.format("\tTranspose (%d, %d) from %s towards (%d, %d) at transposed matrix", i, j, this.name, j, i);
                this.logger.addLog(logMessage, verbose);
            }
        }
        return new Matrix(String.format("(%1$s)^T", this.name), transposedMatrix);
    }

    /**
     * Return the inverse of current matrix
     *
     * @param verbose true | false -> print step by step
     * @return inverse of current matrix
     */
    public Matrix inverse(boolean verbose) {
        if (this.matrix == null) { // check if null
            throw new NullPointerException("Empty matrix");
        }
        if (this.shape(MatrixEnum.COLUMNS_SHAPE) != this.shape(MatrixEnum.ROWS_SHAPE) || !this.matrixUtils.isSquareNonSingular(this.matrix)) {
            throw new IllegalArgumentException("Matrix must be square and non-singular"); // invalid matrix
        }
        int matrixLength = this.shape(MatrixEnum.ROWS_SHAPE);
        this.logger.addLog("Matrix inverse\n\tCheck matrix validity (passed)", verbose);  // Added for verbosity
        Matrix cofactorMatrix = new Matrix(String.format("%1$s^-1", this.name), new double[matrixLength][matrixLength]);
        this.logger.addLog("\tCreated cofactor matrix with size " + matrixLength + "x" + matrixLength, verbose);  // Added for verbosity
        // check factor matrix
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                cofactorMatrix.getMatrix()[i][j] = this.matrixUtils.determinant(this.matrixUtils.minor(matrix, i, j)) * Math.pow(-1, i + j);
                this.logger.addLog("\t\tCalculated cofactor at position (" + i + "," + j + ")", verbose);  // Added for verbosity
            }
        }
        // Calculate determinant of original matrix
        double determinant = this.matrixUtils.determinant(matrix);
        this.logger.addLog("\tCalculated determinant of original matrix: " + determinant, verbose);  // Added for verbosity
        // Invert the cofactor matrix (divide by determinant)
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                cofactorMatrix.getMatrix()[i][j] /= determinant;
                this.logger.addLog("\t\tInverted cofactor at position (" + i + "," + j + ")", verbose);  // Added for verbosity
            }
        }
        cofactorMatrix = cofactorMatrix.transpose(false);
        cofactorMatrix.name = String.format("%1$s^-1", this.name);
        return cofactorMatrix;
    }

    /**
     * this method calls the sub method add, use it if u want to avoid verbose
     *
     * @param matrix the matrix to add to current matrix, return possible value
     * to avoid overwrite
     * @return the sum value
     */
    public Matrix add(Matrix matrix) {
        return this.add(matrix, false);
    }

    /**
     * this method calls the sub method add, use it if u want to avoid verbose
     *
     * @param matrix the matrix to add to current matrix, return possible value
     * to avoid overwrite
     * @param verbose true | false to add a step by step to logger
     * @return the sum value
     */
    public Matrix add(Matrix matrix, boolean verbose) {
        return this.add(matrix, verbose, false);
    }

    /**
     * this method calls the sub method sub, use it if u want to avoid verbose
     *
     * @param matrix the matrix to add to current matrix, return possible value
     * to avoid overwrite
     * @return the sum value
     */
    public Matrix sub(Matrix matrix) {
        return this.sub(matrix, false);
    }

    /**
     * this method calls the sub method add, use it if u want to avoid verbose
     *
     * @param matrix the matrix to add to current matrix, return possible value
     * to avoid overwrite
     * @param verbose true | false to add a step by step to logger
     * @return the sum value
     */
    public Matrix sub(Matrix matrix, boolean verbose) {
        return this.add(matrix, verbose, true);
    }

    /**
     * This method is hybrid, executes an addition, but can send parameter
     * useNegative to cast the matrixToAdd into a negative one, so we can use it
     * as a sub too
     *
     * @param matrixToAdd the [MATRIX] to add / sub
     * @param verbose true | false to add a step by step to Logger
     * @param useNegative true | false if change function to sub
     * @return the value of the addition
     */
    public Matrix add(Matrix matrixToAdd, boolean verbose, boolean useNegative) {
        // check if current matrix or new matrix is empty
        if (this.getMatrix() == null || matrixToAdd.getMatrix() == null) {
            throw new NullPointerException("Una de las matrices es nula, imposible operar");
        }
        // check if the columns at current matrix are the same as the columns of the new matrix
        if (this.shape(MatrixEnum.COLUMNS_SHAPE) != matrixToAdd.shape(MatrixEnum.COLUMNS_SHAPE) && this.shape(MatrixEnum.ROWS_SHAPE) != matrixToAdd.shape(MatrixEnum.ROWS_SHAPE)) {
            throw new IllegalArgumentException(String.format("The rows of %1$s and the columns of %2$s must have the same value, %1$s[%3$d][%4$d] <-> %2$s[%5$d][%6$d]", this.name, matrixToAdd.name, this.shape(MatrixEnum.ROWS_SHAPE), this.shape(MatrixEnum.COLUMNS_SHAPE), matrixToAdd.shape(MatrixEnum.ROWS_SHAPE), matrixToAdd.shape(MatrixEnum.COLUMNS_SHAPE)));
        }
        // print message if verbose
        this.logger.addLog(String.format("Iniciando la %3$s de %1$s con %2$s", this.name, matrixToAdd.name, useNegative ? "resta" : "suma"), verbose);
        try {
            Matrix tmp_matrix = (Matrix) this.clone(); // clone current value to try sasve current values if possible error
            // init addition
            int columnsTmpMatrix = tmp_matrix.shape(MatrixEnum.COLUMNS_SHAPE);
            for (int current_row = 0; current_row < tmp_matrix.shape(MatrixEnum.ROWS_SHAPE); current_row++) {
                for (int current_column = 0; current_column < columnsTmpMatrix; current_column++) {
                    double valueToAdd = useNegative ? -matrixToAdd.getMatrix()[current_row][current_column] : matrixToAdd.getMatrix()[current_row][current_column];
                    tmp_matrix.getMatrix()[current_row][current_column] += valueToAdd;
                    this.logger.addLog(String.format("\t%1$s[%2$d][%3$d] %8$s %4$s[%2$d][%3$d] => %5$f %8$s %6$f = %7$f", tmp_matrix.getName(), current_column, current_row, matrixToAdd.getName(), this.getMatrix()[current_row][current_column], matrixToAdd.getMatrix()[current_row][current_column], tmp_matrix.getMatrix()[current_row][current_column], useNegative ? '-' : '+'), verbose);
                }
            }
            return tmp_matrix;
        } catch (CloneNotSupportedException | IllegalStateException | NullPointerException e) {
            this.logger.addLog(e.getMessage(), true);
            return null;
        }
    }

    /**
     * This function calls sub method multiply, call this method if verbose is
     * false
     *
     * @param matrix3 the matrix to multiply current matrix
     * @return multiplied matrix
     */
    public Matrix multiply(Matrix matrix3) {
        return this.multiply(matrix3, false);
    }

    /**
     * Function to multiply a matrix to current matrix
     *
     * @param matrixToMultiply matrix to multiply
     * @param verbose true | false to print step by step
     * @return matrix || null
     */
    public Matrix multiply(Matrix matrixToMultiply, boolean verbose) {
        // check if any matrix is null
        if (this.getMatrix() == null || matrixToMultiply.getMatrix() == null) {
            throw new NullPointerException("Any matrix is null, no operable matrices");
        }
        // check if SHAPE_COLUMNS CURRENT = SHAPE_ROWS matrixToMultiply
        if (this.shape(MatrixEnum.COLUMNS_SHAPE) != matrixToMultiply.shape(MatrixEnum.ROWS_SHAPE)) {
            throw new IllegalArgumentException(String.format("Rows at matrix %1$s (%2$d) must have same rows tnan matrix %3$s (%4$d)", this.getName(), this.shape(MatrixEnum.COLUMNS_SHAPE), matrixToMultiply.getName(), matrixToMultiply.shape(MatrixEnum.ROWS_SHAPE)));
        }
        //  print if verbose 
        this.logger.addLog(String.format("Multiply init %1$s * %2$s", this.name, matrixToMultiply.getName()), verbose);
        try {
            Matrix resultMatrix = new Matrix(String.format("%1$s*%2$s", this.getName(), matrixToMultiply.getName()), new double[this.shape(MatrixEnum.ROWS_SHAPE)][matrixToMultiply.shape(MatrixEnum.COLUMNS_SHAPE)]);
            // multiply
            int columnsMatrixToMultiply = matrixToMultiply.shape(MatrixEnum.COLUMNS_SHAPE);
            int columnsCurrentMatrix = this.shape(MatrixEnum.COLUMNS_SHAPE);
            for (int current_row = 0; current_row < this.shape(MatrixEnum.ROWS_SHAPE); current_row++) {
                for (int current_column = 0; current_column < columnsMatrixToMultiply; current_column++) {
                    double sum = 0;
                    for (int pivot = 0; pivot < columnsCurrentMatrix; pivot++) {
                        sum += this.matrix[current_row][pivot] * matrixToMultiply.getMatrix()[pivot][current_column];
                        // check if verbose
                        this.logger.addLog(String.format("\t%1$s[%2$d][%3$d] * %4$s[%5$d][%6$d] += %7$f * %8$f = %9$f", this.getName(), current_row, pivot, matrixToMultiply.getName(), pivot, current_column, this.matrix[current_row][pivot], matrixToMultiply.getMatrix()[pivot][current_column], sum), verbose);
                    }
                    resultMatrix.getMatrix()[current_row][current_column] = sum;
                }
            }
            return resultMatrix;
        } catch (Exception e) {
            this.logger.addLog(e.getMessage(), true);
            return null;
        }
    }

    /**
     * Function to divide current matrix by divMatrix
     *
     * @param divMatrix matrix to divide
     * @return matrix divided
     */
    public Matrix div(Matrix divMatrix) {
        return this.div(divMatrix, false);
    }

    /**
     * This function calls to inverse function and then multiply current matrix
     * for inverse of divMatrix
     *
     * @param divMatrix divisor of current matrix
     * @param verbose true | false -> print step by step
     * @return division matrix
     */
    public Matrix div(Matrix divMatrix, boolean verbose) {
        // check if any matrix is null
        if (this.getMatrix() == null || divMatrix.getMatrix() == null) {
            throw new NullPointerException("Una de las matrices es nula, imposible multiplicar");
        }
        // check if SHAPE_COLUMNS CURRENT = SHAPE_ROWS matrixToMultiply
        if ((this.shape(MatrixEnum.COLUMNS_SHAPE) != divMatrix.shape(MatrixEnum.COLUMNS_SHAPE)) || ((this.shape(MatrixEnum.ROWS_SHAPE) != divMatrix.shape(MatrixEnum.ROWS_SHAPE)))) {
            throw new IllegalArgumentException(String.format("Cols's matrix %1$s (%2$d) must be same Row's matrix %3$s (%4$d)", this.getName(), this.shape(MatrixEnum.COLUMNS_SHAPE), divMatrix.getName(), divMatrix.shape(MatrixEnum.ROWS_SHAPE)));
        }
        // check inverse of dividendous
        this.logger.addLog("Check inverse of dividendo", verbose);
        divMatrix = divMatrix.inverse(verbose);

        try {
            // multiply inverse and current matrix
            Matrix copyMatrix = (Matrix) this.clone(); // copy
            copyMatrix = copyMatrix.multiply(divMatrix, verbose); // mult
            return copyMatrix; // return
        } catch (CloneNotSupportedException ex) {
            this.logger.addLog(ex.getMessage(), true);
            return null;
        }
    }

    /**
     * Check if current matrix is inverse
     *
     * @return true or false if inverse exist
     */
    public boolean hasInverse() {
        try {
            if (this.matrix == null) {
                return false;
            }
            return this.matrixUtils.determinant(this.matrix) != 0;
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Matrix unable to get determinant, not operable " + e.getMessage(), true);
            return false;
        }
    }

}

package Model.Matrix.Solver;

import Model.Matrix.Matrix;
import Model.Matrix.MatrixEnum;
import Model.Utils.CustomLogger;
import java.util.Arrays;

/**
 *
 * @author jefe_mayoneso
 */
public class GaussJordanSolver {

    private static GaussJordanSolver instance; // singleton instance

    // singletone constructor
    private GaussJordanSolver() {
    }

    public static GaussJordanSolver getInstance() {
        if (instance == null) {
            instance = new GaussJordanSolver();
        }
        return instance;
    }

    /**
     * Calls a sub method and avoid printing step by step
     *
     * @param solveMatrix
     * @return
     */
    public Matrix solveGauss(Matrix solveMatrix) {
        return this.solveGauss(solveMatrix, false);
    }

    /**
     * Solves a matrix type of [MATRIX] and prints a step by step using logger
     * class saves log to logger global variable
     *
     * @param matrix
     * @param verbose
     * @return the solved matrix || null
     */
    public Matrix solveGauss(Matrix matrix, boolean verbose) {
        // validations
        if (matrix == null) {
            throw new NullPointerException("Empty matrix, cannot do gauss");
        }
        if (matrix.shape(MatrixEnum.COLUMNS_SHAPE) != matrix.shape(MatrixEnum.ROWS_SHAPE) + 1) {
            throw new IllegalArgumentException("Matrix must be nxm, with m = n + 1");
        }

        if (verbose) {
            CustomLogger.getInstance().addLog(String.format("START GAUSS\n%1$s", matrix.toString()));
        }
        try {
            Matrix resultMatrix = (Matrix) matrix.clone();
            //        double[][] resultMatrix = Arrays.stream(matrix.getMatrix()) // clone matrix
            //                .map(double[]::clone)
            //                .toArray(double[][]::new);

            for (int i = 0; i < resultMatrix.getMatrix().length; i++) { // row by row
                for (int j = i + 1; j < resultMatrix.getMatrix().length; j++) { // 
                    double factor = resultMatrix.getMatrix()[j][i] / resultMatrix.getMatrix()[i][i]; // get current factor for pivot
                    for (int k = 0; k < resultMatrix.getMatrix()[i].length; k++) {
                        resultMatrix.getMatrix()[j][k] -= factor * resultMatrix.getMatrix()[i][k]; // execute operation by pivot
                    }
                    if (verbose) {
                        CustomLogger.getInstance().addLog("\tPASO " + (i + 1) + ": FILA " + (j + 1) + " - FILA " + (i + 1) + " * " + factor + " = FILA " + (j + 1));
                    }
                }
            }
            solveSubstitution(resultMatrix, verbose);
            resultMatrix.setName(String.format("GAUSS(%1$s)", resultMatrix.getName()));
            return resultMatrix;
        } catch (CloneNotSupportedException e) {
            CustomLogger.getInstance().addLog("Unable to execute gauss" + e.getMessage());
            return null;
        }
    }

    /**
     * A function to solve a matrix step by step using the A = x + y + RES(PREV
     * STEP)
     *
     * @param preGaussMatrix
     * @return
     */
    private void solveSubstitution(Matrix preGaussMatrix, boolean verbose) {
        double[][] reducedMatrix = preGaussMatrix.getMatrix();
        if (verbose) {
            CustomLogger.getInstance().addLog("\t\tBackwards substitution");
        }
        int n = reducedMatrix.length;
        double[] solutions = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            solutions[i] = reducedMatrix[i][n];
            for (int j = i + 1; j < n; j++) {
                if (verbose) {
                    CustomLogger.getInstance().addLog("\t\t\tx" + i + " = " + solutions[i] + " - " + reducedMatrix[i][j] + " * " + solutions[j]);
                }
                solutions[i] -= reducedMatrix[i][j] * solutions[j];
                // Set the cell to 0 in the reduced matrix
                reducedMatrix[i][j] = 0;
            }
            if (verbose) {
                CustomLogger.getInstance().addLog("\t\t\tx" + i + " = " + solutions[i] + " / " + reducedMatrix[i][i]);
            }
            solutions[i] /= reducedMatrix[i][i];
            // Set the diagonal cell to 1 in the reduced matrix
            reducedMatrix[i][i] = 1;
            if (verbose) {
                CustomLogger.getInstance().addLog("\t\t\t\tR: x" + i + " = " + solutions[i]);
            }
            // Append solution to the last column of the matrix
            reducedMatrix[i][n] = solutions[i];
        }
        if (verbose) {
            CustomLogger.getInstance().addLog("\t\t\t\tResultados:\n\t\t\t\t\t" + Arrays.toString(solutions));
        }
        // No need to return preGaussMatrix since it's modified in place
    }

    /**
     * Call a sub method, avoid printing step by step into logger
     *
     * @param matrix [MATRIX] to solve
     * @return result || null
     */
    public Matrix solveGaussJordan(Matrix matrix) {
        return this.solveGauss(matrix, false);
    }

    /**
     * Solves a [MATRIX] object using just code, no libraries, and print the
     * step by step if needed
     *
     * @param matrix [MATRIX] to solve
     * @param verbose true | false to print step by step
     * @return matrix result || null if error
     */
    public Matrix solveGaussJordan(Matrix matrix, boolean verbose) {
        // validations
        if (matrix == null) {
            throw new NullPointerException("Empty matrix, cannot do gauss");
        }
        if (matrix.shape(MatrixEnum.COLUMNS_SHAPE) != matrix.shape(MatrixEnum.ROWS_SHAPE) + 1) {
            throw new IllegalArgumentException("Matrix must be nxm, with m = n + 1");
        }
        if (verbose) {
            CustomLogger.getInstance().addLog(String.format("START GAUSS JORDAN\n%1$s", matrix.toString()));
        }

        int rows = matrix.shape(MatrixEnum.ROWS_SHAPE);
        int cols = matrix.shape(MatrixEnum.COLUMNS_SHAPE);

        try {
            Matrix tmpMatrix = (Matrix) matrix.clone();
            for (int i = 0; i < rows; i++) {
                // Partial pivoting
                int maxRow = i;
                for (int k = i + 1; k < rows; k++) {
                    if (Math.abs(tmpMatrix.getMatrix()[k][i]) > Math.abs(tmpMatrix.getMatrix()[maxRow][i])) {
                        maxRow = k;
                    }
                }
                // Swap rows
                double[] temp = tmpMatrix.getMatrix()[i];
                tmpMatrix.getMatrix()[i] = tmpMatrix.getMatrix()[maxRow];
                tmpMatrix.getMatrix()[maxRow] = temp;

                if (verbose) {
                    CustomLogger.getInstance().addLog("Final pivote: SWAP ROW R" + (i + 1) + " <-> R" + (maxRow + 1) + "\n" + tmpMatrix.toString());
                }
                // Make the diagonal elements 1
                double divisor = tmpMatrix.getMatrix()[i][i];
                for (int j = 0; j < cols; j++) {
                    tmpMatrix.getMatrix()[i][j] /= divisor;
                }
                if (verbose) {
                    CustomLogger.getInstance().addLog("Reduce diagonal (" + (i + 1) + ", " + (i + 1) + ")\n" + tmpMatrix.toString());
                }
                // Make other elements in the column zero
                for (int k = 0; k < rows; k++) {
                    if (k != i) {
                        double factor = tmpMatrix.getMatrix()[k][i];
                        for (int j = 0; j < cols; j++) {
                            tmpMatrix.getMatrix()[k][j] -= factor * tmpMatrix.getMatrix()[i][j];
                        }
                    }
                }
                if (verbose) {
                    CustomLogger.getInstance().addLog("Elements at " + (i + 1) + " make 0:\n" + tmpMatrix.toString());
                }
            }
            tmpMatrix.setName(String.format("G_JORDAN(%1$s)", tmpMatrix.getName()));
            return tmpMatrix;
        } catch (CloneNotSupportedException e) {
            CustomLogger.getInstance().addLog("Unable to clone matrix" + e.getMessage());
            return null;
        }
    }

}

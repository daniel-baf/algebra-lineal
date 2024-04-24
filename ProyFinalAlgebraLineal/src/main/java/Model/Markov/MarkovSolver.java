/**
 * This class execute Markov chains, using singleton
 */
package Model.Markov;

import Model.Matrix.Matrix;
import Model.Matrix.MatrixEnum;
import Model.Utils.CustomLogger;

/**
 *
 * @author jefe_mayoneso
 */
public class MarkovSolver {

    private static MarkovSolver instance; // singleton instance

    private MarkovSolver() {
    }

    /**
     * Singleton instance
     *
     * @return
     */
    public static MarkovSolver getInstance() {
        if (instance == null) {
            instance = new MarkovSolver();
        }
        return instance;
    }

    /**
     * SOlve Markov avoid printing steps
     *
     * @param key
     * @param originMatrix
     * @param iterations
     * @return
     */
    public Matrix solve(Matrix key, Matrix originMatrix, int iterations) {
        return this.solve(key, originMatrix, iterations, false);
    }

    /**
     * Solve a Markov chain, in this case, evaluate n iterations and return
     * value
     *
     * @param key, matrix key
     * @param originMatrix
     * @param iterations, n ways to iterate
     * @param verbose
     * @return
     */
    public Matrix solve(Matrix key, Matrix originMatrix, int iterations, boolean verbose) {
        // validate key
        if (key == null || originMatrix == null) {
            throw new IllegalArgumentException("Matrix key cannot be null at markov");
        }
        if (originMatrix.shape(MatrixEnum.COLUMNS_SHAPE) != originMatrix.shape(MatrixEnum.ROWS_SHAPE)) {
            throw new IllegalArgumentException("Matrix must be square");
        }
        // check if key sum 100%
        if (!this.rowsSum100Percent(key.transpose(verbose))) {
            throw new IllegalArgumentException("Key do not sum 100%");
        }
        // check row by row the sum is 1
        if (!this.rowsSum100Percent(originMatrix)) {
            throw new IllegalArgumentException("A row doesn't sum 100% (1)");
        }
        originMatrix = originMatrix.transpose(verbose);
        return this.solveMarkov(key, originMatrix, iterations, verbose);
    }

    /**
     * Recursively iterate to solve Markov Chain
     *
     * @param key current key to multiply
     * @param originalMatrix original matrix
     * @param iterations actual iteration
     * @return
     */
    private Matrix solveMarkov(Matrix key, Matrix originalMatrix, int iteration, boolean verbose) {
        if (iteration <= 0) {
            return key;
        }
        // multiply originalMatrix and key
        CustomLogger.getInstance().addLog(String.format("Markov iteration no. %1$d, multiply key * matrix", iteration), verbose);
        key = originalMatrix.multiply(key, verbose);
        return this.solveMarkov(key, originalMatrix, --iteration, verbose);
    }

    /**
     * Check if all rows of current matrix sum 100
     *
     * @param originMatrix
     * @return
     */
    private boolean rowsSum100Percent(Matrix originMatrix) {
        for (int row = 0; row < originMatrix.shape(MatrixEnum.ROWS_SHAPE); row++) {
            double tmpSum = 0;
            for (int col = 0; col < originMatrix.shape(MatrixEnum.COLUMNS_SHAPE); col++) {
                tmpSum += originMatrix.getMatrix()[row][col];
            }
            if (tmpSum != 1) {
                return false;
            }
        }
        return true;
    }

}

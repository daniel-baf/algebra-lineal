package Model.Matrix.Utils;

/**
 * Singleton class for matrix utility functions
 */
public class MatrixUtils {

    // Singleton instance
    private static MatrixUtils instance;

    // Private constructor to prevent instantiation
    private MatrixUtils() {
    }

    // Method to get the singleton instance
    public static MatrixUtils getInstance() {
        if (instance == null) {
            instance = new MatrixUtils();
        }
        return instance;
    }

    /**
     * Get minor matrix (excluding row i and column j)
     *
     * @param matrix the matrix to get minor value
     * @param i current index row
     * @param j current index col
     * @return minor pivot in matrix
     */
    public double[][] minor(double[][] matrix, int i, int j) {
        int n = matrix.length;
        double[][] minorMatrix = new double[n - 1][n - 1];
        int row = 0, col = 0;
        for (int m = 0; m < n; m++) {
            for (int k = 0; k < n; k++) {
                if (m != i && k != j) {
                    minorMatrix[row][col] = matrix[m][k];
                    col++;
                    if (col == n - 1) {
                        col = 0;
                        row++;
                    }
                }
            }
        }
        return minorMatrix;
    }


    /**
     * Check determinant using co factors
     *
     * @param matrix matrix to check determinant
     * @return the current determinant
     */
    public double determinant(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }
        double det = 0;
        for (int j = 0; j < n; j++) {
            det += Math.pow(-1, j) * matrix[0][j] * determinant(minor(matrix, 0, j));
        }
        return det;
    }

    /**
     * Calculate the determinant of matrix using Sarrous
     *
     * @param matrix matrix to check
     * @param verbose true or false step by step
     * @return determinant using sarrus of a matrix
     */
    public double determinantSarrous(double[][] matrix, boolean verbose) {
        return SarrusSolver.getInstance().solveSarrus(matrix, verbose);
    }

    /**
     * Check the range of a function using reduction of matrices
     *
     * @param matrix matrix to check
     * @return n rows independient
     */
    public int countLinearlyIndependentRows(double[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Empty matrix");
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        int rank = 0;
        boolean[] rowVisited = new boolean[rows]; // Keep track of visited rows

        // Reduce the matrix to row echelon form
        for (int col = 0; col < cols; col++) {
            int pivotRow = rank; // Pivot row index
            while (pivotRow < rows && (matrix[pivotRow][col] == 0 || rowVisited[pivotRow])) {
                pivotRow++; // Find the pivot row
            }
            if (pivotRow == rows) {
                continue; // No pivot in this column, move to the next column
            }
            rowVisited[pivotRow] = true;

            // Swap pivot row with current rank row
            double[] temp = matrix[rank];
            matrix[rank] = matrix[pivotRow];
            matrix[pivotRow] = temp;

            // Reduce rows below the pivot row
            for (int i = rank + 1; i < rows; i++) {
                double factor = matrix[i][col] / matrix[rank][col];
                for (int j = col; j < cols; j++) {
                    matrix[i][j] -= factor * matrix[rank][j];
                }
            }

            rank++; // Increment rank
        }

        return rank;
    }



    /**
     * Check if matrix is square and has non-zero determinant
     *
     * @param matrix the matrix to check
     * @return true or false if matrix is nxn
     */
    public boolean isSquareNonSingular(double[][] matrix) {
        int n = matrix.length;
        return n == matrix[0].length && this.determinant(matrix) != 0;
    }

}

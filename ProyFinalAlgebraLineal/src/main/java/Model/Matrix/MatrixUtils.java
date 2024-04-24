package Model.Matrix;

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
     * @param i
     * @param j
     * @return
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
     * Check if matrix is square and has non-zero determinant
     *
     * @param matrix
     * @return
     */
    public boolean isSquareNonSingular(double[][] matrix) {
        int n = matrix.length;
        return n == matrix[0].length && determinant(matrix) != 0;
    }

    // Calculate determinant of a matrix
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
}

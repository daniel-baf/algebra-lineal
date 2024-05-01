package Model.Matrix.Utils;

import Model.Utils.CustomLogger;

/**
 *
 * @author jefe_mayoneso
 */
public class SarrusSolver {

    private static SarrusSolver instance;

    private SarrusSolver() {
        // Private constructor to prevent instantiation
    }

    public static SarrusSolver getInstance() {
        if (instance == null) {
            instance = new SarrusSolver();
        }
        return instance;
    }

    /**
     * Solve a 3x3 function using sarrous
     *
     * @param mat matrix
     * @param verbose true or false to print steps
     * @return determinant using sarrus
     */
    public double solveSarrus(double[][] mat, boolean verbose) {
        if (mat == null) {
            throw new NullPointerException("Matrix is null");
        }

        try {
            int rows = mat.length;
            int cols = mat[0].length;
            if (rows != cols) {
                throw new IllegalArgumentException("Matrix must be square");
            }

            double determinant = 0;

            switch (rows) {
                case 2 -> {
                    determinant = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];
                    CustomLogger.getInstance().addLog("Determinant calculated: " + determinant, verbose);
                }
                case 3 -> {
                    CustomLogger.getInstance().addLog("Calculating determinant using Sarrus rule...", verbose);
                    for (int i = 0; i < rows; i++) {
                        double product = 1;
                        for (int j = 0; j < rows; j++) {
                            product *= mat[j][(i + j) % rows]; // Calculate product for each diagonal
                        }
                        determinant += product;
                        CustomLogger.getInstance().addLog("Partial determinant after diagonal " + (i + 1) + ": " + determinant, verbose);
                    }
                    for (int i = 0; i < rows; i++) {
                        double product = 1;
                        for (int j = rows - 1; j >= 0; j--) {
                            product *= mat[j][(i - j + rows) % rows]; // Calculate product for each anti-diagonal
                        }
                        determinant -= product;
                        CustomLogger.getInstance().addLog("Partial determinant after anti-diagonal " + (i + 1) + ": " + determinant, verbose);
                    }
                    CustomLogger.getInstance().addLog("Determinant calculated: " + determinant, verbose);
                }
                default ->
                    throw new IllegalArgumentException("Sarrus can only be used with 2x2 or 3x3 matrices");
            }
            return determinant;
        } catch (IllegalArgumentException | NullPointerException e) {
            CustomLogger.getInstance().addLog(e.getMessage(), true);
            return 0;
        }
    }
}

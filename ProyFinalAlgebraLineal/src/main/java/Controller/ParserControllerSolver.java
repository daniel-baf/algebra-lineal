package Controller;

import Model.Compiler.ParserModel;
import Model.Matrix.Matrix;
import Model.Matrix.MatrixEnum;
import Model.Matrix.Utils.MatrixUtils;
import Model.Utils.CustomLogger;

public class ParserControllerSolver<T> {

    public ParserModel<T> model;

    public ParserControllerSolver(ParserModel model) {
        this.model = model;
    }

    /**
     * Solve a rank declaration in pool hashmap
     *
     * @param matrixName matrixname
     * @param verbose    true or false to print step by step
     */
    public void solveRankPool(String matrixName, boolean verbose) {
        int rank = MatrixUtils.getInstance().countLinearlyIndependentRows(this.model.getMatrices().get(matrixName).getMatrix(), verbose);
        CustomLogger.getInstance().addLog(String.format("\tFINAL RANK: %1$d ", rank), verbose);
    }

    /**
     * Solve all inverses stacked in the hashmpa for inverses
     *
     * @param matrixName matrix name to search in hashmpap
     * @param verbose    true or false to print step by step
     */
    public void solveInversePool(String matrixName, boolean verbose) {
        try {
            Matrix copyMatrix = (Matrix) this.model.getMatrices().get(matrixName).clone();
            copyMatrix = copyMatrix.inverse(verbose);
            CustomLogger.getInstance().addLog(copyMatrix.toString(), verbose);
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Instruction no valid for matrix " + matrixName + " avoiding this inverse " + e.getMessage(), true);
        }
    }

    /**
     * Solve calculation of determinant for matrices, if 3x3 uses sarrous, otherwise use co factors
     *
     * @param matrixName the matrix name to search in hashmap
     * @param verbose    true or false step by step
     */
    public void solveDeterminantPool(String matrixName, boolean verbose) {
        try {
            Matrix copyMatrix = (Matrix) this.model.getMatrices().get(matrixName).clone();
            // check size of matrix
            if (MatrixUtils.getInstance().isSquareNonSingular(copyMatrix.getMatrix()) && copyMatrix.shape(MatrixEnum.COLUMNS_SHAPE) == 3) {
                double det = MatrixUtils.getInstance().determinantSarrous(copyMatrix.getMatrix(), verbose);
                CustomLogger.getInstance().addLog("DETERMINANT: " + det, true);
            } else {
                double det = MatrixUtils.getInstance().determinant(copyMatrix.getMatrix(), verbose);
                CustomLogger.getInstance().addLog("DETERMINANT: " + det, true);
            }
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Instruction no valid for matrix " + matrixName + " avoiding this inverse " + e.getMessage(), true);
        }
    }
}

package Controller;

import Domain.AVL.NodeAVL;
import Domain.Markov.MarkovData;
import Domain.Vector.GraphVector;
import Domain.Vector.GraphVectorSolver;
import Model.Compiler.CommonParserHashKey;
import Model.Compiler.ParserModel;
import Model.Encrypter.Crypter;
import Model.Markov.MarkovSolver;
import Model.Matrix.Matrix;
import Model.Matrix.MatrixEnum;
import Model.Matrix.Solver.ArithAVLSolver;
import Model.Matrix.Solver.GaussJordanSolver;
import Model.Matrix.Utils.MatrixUtils;
import Model.Utils.CustomLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParserControllerSolver<T> {

    public ParserModel<T> model;

    public ParserControllerSolver(ParserModel<T> model) {
        this.model = model;
    }

    /**
     * Print detailed data for all operations
     */
    public void printSummaryOperations() {
        // iterate over hashmap and print all values
        // Using enhanced for loop to iterate over the HashMap
        CustomLogger.getInstance().addTitleLog("MATRICES DECLARED: ", true);
        HashMap<String, Matrix> matrices = this.model.getMatrices();
        for (Map.Entry<String, Matrix> entry : matrices.entrySet()) {
//            String matrixName = entry.getKey();
            Matrix matrix = entry.getValue();
            CustomLogger.getInstance().addLog(matrix.toString(), true);
        }
        /* print all values from hashes strings */
        CustomLogger.getInstance().addTitleLog("OPERATION DECLARED: ", true);

        HashMap<CommonParserHashKey, ArrayList<T>> commonHashes = this.model.getKeysArrayListHashMap();
        for (Map.Entry<CommonParserHashKey, ArrayList<T>> entry : commonHashes.entrySet()) {
            CommonParserHashKey key = entry.getKey();
            ArrayList<T> value = entry.getValue();
            CustomLogger.getInstance().addLog(String.format("KEY [%1$s]: %2$s", key, value), true);
        }
        CustomLogger.getInstance().addSectionEnd(3, true);
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
        CustomLogger.getInstance().addSectionEnd(2, verbose);
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
            CustomLogger.getInstance().addLog(copyMatrix.toString(), true);
            CustomLogger.getInstance().addSectionEnd(2, verbose);
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
            CustomLogger.getInstance().addSectionEnd(2, verbose);
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Instruction no valid for matrix " + matrixName + " avoiding this inverse " + e.getMessage(), true);
        }
    }

    /**
     * Solve all operations about encrypt words using the mapping
     *
     * @param message to encrypt
     * @param verbose true or false to print step by step
     */
    public void solveEncryptPool(String message, boolean verbose) {
        try {
            // call encrypt function
            Matrix resultMatrix = Crypter.getInstance().encrypt(message, verbose);
            int[] dataVector = Crypter.getInstance().matrixToIntegerArray(resultMatrix.getMatrix());
            CustomLogger.getInstance().addLog(String.format("\tFINAL MESSAGE: %1$s", Arrays.toString(dataVector)), true);
            CustomLogger.getInstance().addSectionEnd(2, verbose);
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Instruction no valid for string " + message + " avoiding this encrypt " + e.getMessage(), true);
        }
    }

    /**
     * Execute an operation to decrypt a instruction
     *
     * @param decryptInstruction data to decrypt
     * @param verbose            true or false step by step
     */
    public void solveDecryptPool(ArrayList<Double> decryptInstruction, boolean verbose) {
        try {
            // call encrypt function
            // Convert ArrayList<Integer> to int[]
            int[] intArray = decryptInstruction.stream().mapToInt(Double::intValue).toArray();
            String decryptedText = Crypter.getInstance().decrypt(intArray, verbose);
            CustomLogger.getInstance().addLog(String.format("\tFINAL MESSAGE: %1$s", decryptedText), true);
            CustomLogger.getInstance().addSectionEnd(2, verbose);
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Instruction no valid for vector,  avoiding this encrypt " + e.getMessage(), true);
        }
    }

    /**
     * Solve operations of Gauss or Gauss JOrdan in the pool
     *
     * @param matrixName name to search in hash
     * @param verbose    true or false to print step by step
     * @param operation  typeof operation
     */
    public void solveGaussGJordanPool(String matrixName, boolean verbose, CommonParserHashKey operation) {
        try {
            Matrix copyMatrix = (Matrix) this.model.getMatrices().get(matrixName).clone();
            copyMatrix = switch (operation) {
                case GAUSS -> GaussJordanSolver.getInstance().solveGauss(copyMatrix, verbose);
                case JORDAN -> GaussJordanSolver.getInstance().solveGaussJordan(copyMatrix, verbose);
                default -> null;
            };
            // call gauss function
            CustomLogger.getInstance().addLog(String.format("\tFINAL MATRIX: %1$s", copyMatrix), true);
            CustomLogger.getInstance().addSectionEnd(2, verbose);
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Instruction no valid for matrix " + matrixName + " avoiding this gauss operation " + e.getMessage(), true);
        }
    }

    /**
     * Solve all markov operations implementing a file txt markov data
     *
     * @param markovOperation operation 1
     * @param verbose         true or false to print step by step
     */
    public void solveMarkovPool(MarkovData markovOperation, boolean verbose) {
        try {
            // cast data to markov
            Matrix transitionMatrix = this.model.getMatrices().get(markovOperation.getMatrix());
            Matrix initialStatus = this.model.getMatrices().get(markovOperation.getStatus());
            int iterations = markovOperation.getIterations();
            // call solver
            Matrix result = MarkovSolver.getInstance().solve(initialStatus, transitionMatrix, iterations, verbose);
            CustomLogger.getInstance().addLog(String.format("\tFinal status after %1$d iterations: %2$s", iterations, result.toString()), true);
            CustomLogger.getInstance().addSectionEnd(2, verbose);
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Instruction no valid for matrix " + markovOperation.toString() + " avoiding this gauss operation " + e.getMessage(), true);
        }
    }

    /**
     * Solve each arithmetical node to get final result of matrix operations
     *
     * @param rootNode main node of AVL tree
     * @param verbose  true or false to print step by steps
     */
    public void solveArithmeticalPool(NodeAVL<T> rootNode, boolean verbose) {
        try {
            ArithAVLSolver<T> arithmeticalSolver = new ArithAVLSolver<>(this.model.getMatrices());
            Matrix result = arithmeticalSolver.solve(rootNode, verbose);
            CustomLogger.getInstance().addLog("\tFINAL MATRIX AFTER ARITHMETICAL OPERATION: " + result, true);
            CustomLogger.getInstance().addSectionEnd(2, verbose);
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Instruction no valid for matrix " + rootNode + " avoiding this gauss operation " + e.getMessage(), true);
        }
    }

    /**
     * Solve all needed data from all vectors declared
     *
     * @param vectors list of vectos to solve
     */
    public void solveVectorsFillable(HashMap<String, GraphVector> vectors, boolean verbose) {
        try {
            GraphVectorSolver gSolver = new GraphVectorSolver();
            vectors.forEach((s, vector) -> gSolver.solve(vector, verbose));
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Instruction no valid for vectors,  avoiding this vector solve " + e.getMessage(), true);
        }
    }

    /**
     * Solve all operation related to a vector, dot oper, x operation, add or sub
     *
     * @param rootNode main node to solve
     * @param verbose  true or false to print step by step
     */
    public void solveArithmeticalVectorPool(NodeAVL<GraphVector> rootNode, boolean verbose) {
        CustomLogger.getInstance().addLog("TODO resolve operation for vectors: " + rootNode, verbose);
    }
}

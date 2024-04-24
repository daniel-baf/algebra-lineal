
import Model.Encrypter.Crypter;
import Model.Markov.MarkovSolver;
import Model.Matrix.Matrix;
import Model.Utils.CustomLogger;
import Model.Matrix.MatrixEnum;
import Model.Matrix.Solver.GaussJordanSolver;
import Model.Matrix.Utils.SarrusSolver;

/**
 *
 * @author jefe_mayoneso
 */
public class Main {

    // create testing matrix
    private final double[][] data, data2, data3, data4, data5, data6;  // initialize with = {{},{}] ... inline

    // tmp matrix
    private final Matrix matrix, matrix2, matrix3, matrix4, matrix5;
    private final CustomLogger logger;

    public Main() {
        this.data = new double[][]{{1, 2, 3}, {2, 1, 3}};
        this.data2 = new double[][]{{3, 1, 4}, {8, -1, 0}};
        this.data3 = new double[][]{{1, 0}, {4, -2}, {4, 9}};
        this.data4 = new double[][]{{1, 0, 9, -2}, {4, -2, 9, 1}, {2, -1, 4, 9}, {1, 0, 0, -41}};
        this.data5 = new double[][]{{5, 3, 7, 8}, {-2, 1, 0, 4}, {9, 6, -1, 2}, {11, -5, 13, 14}};
        this.data6 = new double[][]{{1, 2, 3}, {0, -31, 4}, {2, 4, 6}};

        // tmp matrix
        this.matrix = new Matrix("A", this.data);
        this.matrix2 = new Matrix("B", this.data2);
        this.matrix3 = new Matrix("C", this.data3);
        this.matrix4 = new Matrix("D", this.data4);
        this.matrix5 = new Matrix("E", this.data5);
        // logger
        this.logger = CustomLogger.getInstance();
    }

    public static void main(String[] args) {
        // operations object
        Main main = new Main();
        try {
//            main.testShapes();
//            main.testAddition(false);
//            main.testSubstract(false);
//            main.testMultiply(false);
//            main.testTranspose(true);
//            main.testInverse(true);
//            main.testDivide(true);
//            main.testGauss(true);
//            main.testGaussJordan(true);
//            main.testSarrous(true);
//            main.testRankMatrix(true);
            Matrix tmpMatrix = main.testEncrypt(false);
            main.testDecrypt(tmpMatrix, true);
//            main.testMarkov(true);
            // TODO imaginary operations

            main.logger.printLogs();
            main.logger.deleteLogs();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void testShapes(boolean verbose) {
        // print shape
        this.logger.addLog(String.format("MATRIX SAHPE%1s\n\trows: %2d\n\tcolumns: %3d", this.matrix.getName(), this.matrix.shape(MatrixEnum.ROWS_SHAPE), this.matrix.shape(MatrixEnum.COLUMNS_SHAPE)), verbose);
    }

    private void testAddition(boolean verbose) throws CloneNotSupportedException {
        // execute addition
        // add matrix2 to a tmp matrix
        Matrix copyMatrix = (Matrix) this.matrix.clone();
        copyMatrix = copyMatrix.add(this.matrix2, verbose);
        this.logger.addLog("ADD matrices A Y B", verbose);
        if (copyMatrix != null) {
            this.logger.addLog(copyMatrix.toString(), verbose);
        } else {
            this.logger.addLog("unable to add matrices", verbose);
        }

    }

    private void testSubstract(boolean verbose) throws CloneNotSupportedException {
        // execute addition
        // add matrix2 to a tmp matrix
        Matrix copyMatrix = (Matrix) this.matrix.clone();
        copyMatrix = copyMatrix.sub(this.matrix2, verbose);
        this.logger.addLog("SUB matrices A Y B", verbose);
        if (copyMatrix != null) {
            this.logger.addLog(copyMatrix.toString(), verbose);
        } else {
            this.logger.addLog("Unable to sub matrix", verbose);
        }
    }

    private void testMultiply(boolean verbose) throws CloneNotSupportedException {
        // copy matrix
        Matrix copyMatrix = (Matrix) this.matrix.clone();
        copyMatrix = copyMatrix.multiply(this.matrix3, verbose);
        this.logger.addLog(copyMatrix.toString(), verbose);
    }

    private void testTranspose(boolean verbose) throws CloneNotSupportedException {
        // copy matrix
        Matrix copyMatrix = (Matrix) this.matrix.clone();
        copyMatrix = copyMatrix.transpose(verbose);
        this.logger.addLog(copyMatrix.toString(), verbose);
    }

    private void testInverse(boolean verbose) throws CloneNotSupportedException {
        // copy matrix
        Matrix copyMatrix;
        copyMatrix = (Matrix) this.matrix4.clone();
        copyMatrix = copyMatrix.inverse(verbose);
        this.logger.addLog(copyMatrix.toString(), verbose);
    }

    private void testDivide(boolean verbose) throws CloneNotSupportedException {
        // copy matrix
        Matrix copyMatrix;
        copyMatrix = (Matrix) this.matrix4.clone();
        copyMatrix = copyMatrix.div(this.matrix5, verbose);
        this.logger.addLog(copyMatrix.toString(), verbose);
    }

    private void testGauss(boolean verbose) {
        Matrix solvedGauss = GaussJordanSolver.getInstance().solveGauss(this.matrix2, verbose);
        if (solvedGauss != null) {
            this.logger.addLog("SOLVED MATRIX " + this.matrix2.getName(), verbose);
            this.logger.addLog(solvedGauss.toString(), verbose);
        }
    }

    private void testGaussJordan(boolean verbose) {
        Matrix solvedGaussJordan = GaussJordanSolver.getInstance().solveGaussJordan(this.matrix2, verbose);
        if (solvedGaussJordan != null) {
            this.logger.addLog("SOLVED MATRIX " + this.matrix2.getName(), verbose);
            this.logger.addLog(solvedGaussJordan.toString(), verbose);
        }
    }

    private void testSarrous(boolean verbose) {
        double determinantBySarrous = SarrusSolver.getInstance().solveSarrus(this.data6, verbose);
        this.logger.addLog(Double.toString(determinantBySarrous), verbose);
    }

    private void testRankMatrix(boolean verbose) {
        int rank = this.matrix2.getMatrixUtils().countLinearlyIndependentRows(this.data6);
        this.logger.addLog(Integer.toString(rank), verbose);
    }

    private Matrix testEncrypt(boolean verbose) {

        Matrix encrypMatrix = Crypter.getInstance().encrypt("PATO POLLO", verbose);
        if (encrypMatrix != null) {
            this.logger.addLog("ENCRYPTED MATRIX:\n" + encrypMatrix.toString(), true);
        }
        return encrypMatrix;
    }

    private void testDecrypt(Matrix matrix, boolean verbose) {
        // code to decrypt testing
        String decryptedText = Crypter.getInstance().decrypt(matrix, verbose);
        this.logger.addLog("DESENCRYPTED: " + decryptedText, verbose);

    }

    private void testMarkov(boolean verbose) throws CloneNotSupportedException {
        double[][] markovData = {{0.8, 0.1, 0.1}, {0.03, 0.95, 0.02}, {0.2, 0.05, 0.75}};
        Matrix markovProb = new Matrix("MARKOV", markovData);
        Matrix key = new Matrix("KEY MARKOV", new double[][]{{0.45}, {0.25}, {0.30}});
        int iterations = 2;
        // markov key -> 
        markovProb = MarkovSolver.getInstance().solve(key, markovProb, iterations, verbose);
        if (markovProb != null) {
            CustomLogger.getInstance().addLog("MARKOV FINAL\n" + markovProb, verbose);
        }
    }
}

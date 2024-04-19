
import Model.Matrix.Matrix;
import Model.Utils.CustomLogger;
import Model.Matrix.MatrixEnum;

/**
 *
 * @author jefe_mayoneso
 */
public class Main {

    // create testing matrix
    private final double[][] data, data2, data3, data4, data5;  // initialize with = {{},{}] ... inline

    // tmp matrix
    private final Matrix matrix, matrix2, matrix3, matrix4, matrix5;
    private final CustomLogger logger;

    public Main() {
        this.data = new double[][]{{1, 2, 3}, {2, 1, 3}};
        this.data2 = new double[][]{{3, 1, 4}, {8, -1, 0}};
        this.data3 = new double[][]{{1, 0}, {4, -2}, {4, 9}};
        this.data4 = new double[][]{{1, 0, 9, -2}, {4, -2, 9, 1}, {2, -1, 4, 9}, {1, 0, 0, -41}};
        this.data5 = new double[][]{{5, 3, 7, 8}, {-2, 1, 0, 4}, {9, 6, -1, 2}, {11, -5, 13, 14}};

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
            main.testDivide(true);
            // TODO gauss
            // TODO gauss jordan
            // TODO sarrous
            // TODO imaginary operations

            main.logger.printLogs();
            main.logger.deleteLogs();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void testShapes() {
        // print shape
        this.logger.addLog(String.format("MATRIX SAHPE%1s\n\trows: %2d\n\tcolumns: %3d", this.matrix.getName(), this.matrix.shape(MatrixEnum.ROWS_SHAPE), this.matrix.shape(MatrixEnum.COLUMNS_SHAPE)));
    }

    private void testAddition(boolean verbose) throws CloneNotSupportedException {
        // execute addition
        // add matrix2 to a tmp matrix
        Matrix copyMatrix = (Matrix) this.matrix.clone();
        copyMatrix = copyMatrix.add(this.matrix2, verbose);
        this.logger.addLog("ADD matrices A Y B");
        if (copyMatrix != null) {
            this.logger.addLog(copyMatrix.toString());
        } else {
            this.logger.addLog("unable to add matrices");
        }

    }

    private void testSubstract(boolean verbose) throws CloneNotSupportedException {
        // execute addition
        // add matrix2 to a tmp matrix
        Matrix copyMatrix = (Matrix) this.matrix.clone();
        copyMatrix = copyMatrix.sub(this.matrix2, verbose);
        this.logger.addLog("SUB matrices A Y B");
        if (copyMatrix != null) {
            this.logger.addLog(copyMatrix.toString());
        } else {
            this.logger.addLog("Unable to sub matrix");
        }
    }

    private void testMultiply(boolean verbose) throws CloneNotSupportedException {
        // copy matrix
        Matrix copyMatrix = (Matrix) this.matrix.clone();
        copyMatrix = copyMatrix.multiply(this.matrix3, verbose);
        this.logger.addLog(copyMatrix.toString());
    }

    private void testTranspose(boolean verbose) throws CloneNotSupportedException {
        // copy matrix
        Matrix copyMatrix = (Matrix) this.matrix.clone();
        copyMatrix = copyMatrix.transpose(verbose);
        this.logger.addLog(copyMatrix.toString());
    }

    private void testInverse(boolean verbose) throws CloneNotSupportedException {
        // copy matrix
        Matrix copyMatrix;
        copyMatrix = (Matrix) this.matrix4.clone();
        copyMatrix = copyMatrix.inverse(verbose);
        this.logger.addLog(copyMatrix.toString());
    }

    private void testDivide(boolean verbose) throws CloneNotSupportedException {
        // copy matrix
        Matrix copyMatrix;
        copyMatrix = (Matrix) this.matrix4.clone();
        copyMatrix = copyMatrix.div(this.matrix5, verbose);
        this.logger.addLog(copyMatrix.toString());
    }
}

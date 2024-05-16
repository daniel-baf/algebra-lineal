package Model.Compiler;

import Controller.ParserControllerSolver;
import Domain.AVL.NodeAVL;
import Domain.Markov.MarkovData;
import Domain.Vector.GraphVector;
import Model.Matrix.Matrix;
import Model.Utils.CustomLogger;

import java.util.ArrayList;
import java.util.Objects;

public class ParserController<T> {

    private ParserModel<T> model;
    private final ParserControllerSolver<T> solver;

    public ParserController(ParserModel<T> model) {
        this.model = model;
        this.solver = new ParserControllerSolver<>(this.model);
    }

    public ParserModel<T> getModel() {
        return model;
    }

    public void setModel(ParserModel<T> model) {
        this.model = model;
    }

    /**
     * Saves the value of Matrix into model
     *
     * @param newMatrix matrix to save
     */
    public void saveMatrix(Matrix newMatrix) {
        // check if current value exist in hashmap
        boolean valueExist = Objects.isNull(this.model.getMatrices().get(newMatrix.getName()));
        if (valueExist) {
            this.model.getMatrices().put(newMatrix.getName(), newMatrix);
        }
        CustomLogger.getInstance().addLog("MATRIX EXIST, AVOIDING IT", !valueExist);
    }

    /**
     * Save a list of matrices and avoid duplicates
     *
     * @param vectors
     */
    public void saveVector(ArrayList<GraphVector> vectors) {
        vectors.forEach(vector -> {
            boolean valueExist = Objects.isNull(this.model.getVectors().get(vector.getName()));
            if (valueExist) {
                this.model.getVectors().put(vector.getName(), vector);
            }
            CustomLogger.getInstance().addLog(String.format("AVOIDING ADDITION OF VECTOR WITH NAME %1$s, it already exists ", vector.getName()), !valueExist);
        });
    }

    /**
     * Saves in the hashmap common parameter an entry set depending on CommonHashKeys
     *
     * @param key   the key of hashmap
     * @param value the value to add to hashmap
     */
    public void saveInStringHashmap(CommonParserHashKey key, ArrayList<T> value) {
        ArrayList<T> existingValues = this.model.getKeysArrayListHashMap().get(key);
        boolean isEmpty = Objects.isNull(this.model.getKeysArrayListHashMap().get(key));

        if (isEmpty) {
            // Key doesn't exist, create a new one and add the values
            this.model.getKeysArrayListHashMap().put(key, new ArrayList<>(value));
            return;
        }
        // Key exists, check if the types are compatible
        if (!existingValues.isEmpty() && !existingValues.get(0).getClass().equals(value.get(0).getClass())) {
            // Types are not compatible, return
            return;
        }
        // All conditions are met, add values to the existing ArrayList
        existingValues.addAll(value);
    }

    /**
     * Saves hash of numbers to decrypt vectors
     *
     * @param numbers new array to decrypt
     */
    public void saveDecrypts(ArrayList<Double> numbers) {
        if (Objects.isNull(numbers)) return;
        if (numbers.isEmpty()) return; // no operable
        // cast arraylist to double[] values
        ArrayList<ArrayList<Double>> data = new ArrayList<>() {{
            add(numbers);
        }};
        this.saveInStringHashmap(CommonParserHashKey.DECRYPT, (ArrayList<T>) data); // call sub method
    }

    /**
     * Solve all operation stacked in the hashmap one by one
     *
     * @param verbose true or false to print step by step
     */
    public void solve(boolean verbose) {
        ArrayList<T> tmpData;
        // print summary
        this.solver.printSummaryOperations();
        // solve all inverses, call inverse solver
        tmpData = this.model.getKeysArrayListHashMap().get(CommonParserHashKey.INVERSE);
        if (!Objects.isNull(tmpData)) {
            CustomLogger.getInstance().addTitleLog("INVERSE OPERATIONS", verbose);
            tmpData.forEach(inverseInstruction -> this.solver.solveInversePool(inverseInstruction.toString(), verbose));
        }
        tmpData = this.model.getKeysArrayListHashMap().get(CommonParserHashKey.DETERMINANT);
        if (!Objects.isNull(tmpData)) {
            // call determinant function
            CustomLogger.getInstance().addTitleLog("DETERMINANT OPERATIONS", verbose);
            tmpData.forEach(determinantInstruction -> this.solver.solveDeterminantPool(determinantInstruction.toString(), verbose));
        }
        tmpData = this.model.getKeysArrayListHashMap().get(CommonParserHashKey.RANK);
        if (!Objects.isNull(tmpData)) {
            // call all rank declaration
            CustomLogger.getInstance().addTitleLog("RANK OPERATIONS", verbose);
            tmpData.forEach(rankInstruction -> this.solver.solveRankPool(rankInstruction.toString(), verbose));
        }
        tmpData = this.model.getKeysArrayListHashMap().get(CommonParserHashKey.ENCRYPT);
        if (!Objects.isNull(tmpData)) {
            // execute all encrypts
            CustomLogger.getInstance().addTitleLog("ENCRYPT WORDS", verbose);
            this.model.getKeysArrayListHashMap().get(CommonParserHashKey.ENCRYPT).forEach(encryptInstruction -> this.solver.solveEncryptPool(encryptInstruction.toString(), verbose));
        }
        tmpData = this.model.getKeysArrayListHashMap().get(CommonParserHashKey.DECRYPT);
        if (!Objects.isNull(tmpData)) {
            // execute all decrypt
            CustomLogger.getInstance().addTitleLog("DECRYPT WORDS", verbose);
            tmpData.forEach(decryptInstruction -> this.solver.solveDecryptPool((ArrayList<Double>) decryptInstruction, verbose));
        }
        tmpData = this.model.getKeysArrayListHashMap().get(CommonParserHashKey.GAUSS);
        if (!Objects.isNull(tmpData)) {
            // execute all gauss jordan
            CustomLogger.getInstance().addTitleLog("GAUSS OPERATIONS", verbose);
            tmpData.forEach(gaussInstruction -> this.solver.solveGaussGJordanPool(gaussInstruction.toString(), verbose, CommonParserHashKey.JORDAN));
        }
        tmpData = this.model.getKeysArrayListHashMap().get(CommonParserHashKey.GAUSS);
        if (!Objects.isNull(tmpData)) {
            // execute all gauss
            CustomLogger.getInstance().addTitleLog("GAUSS JORDAN OPERATIONS", verbose);
            tmpData.forEach(gaussInstruction -> this.solver.solveGaussGJordanPool(gaussInstruction.toString(), verbose, CommonParserHashKey.GAUSS));
        }
        tmpData = this.model.getKeysArrayListHashMap().get(CommonParserHashKey.MARKOV);
        if (!Objects.isNull(tmpData)) {
            // execute all markov instances
            CustomLogger.getInstance().addTitleLog("MARKOV OPERATIONS", verbose);
            tmpData.forEach(markovOperation -> this.solver.solveMarkovPool((MarkovData) markovOperation, verbose));
        }
        tmpData = this.model.getKeysArrayListHashMap().get(CommonParserHashKey.ARITH_MATRIX);
        if (!Objects.isNull(this.model.getKeysArrayListHashMap().get(CommonParserHashKey.ARITH_MATRIX))) {
            // arithmetical operations
            CustomLogger.getInstance().addTitleLog("ARITHMETICAL OPERATIONS", verbose);
            tmpData.forEach(arithmeticalOperation -> this.solver.solveArithmeticalPool((NodeAVL<T>) arithmeticalOperation, verbose));
        }
    }


}

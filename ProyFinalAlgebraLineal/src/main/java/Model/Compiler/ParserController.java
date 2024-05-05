package Model.Compiler;

import Controller.ParserControllerSolver;
import Domain.AVL.NodeAVL;
import Domain.Markov.MarkovData;
import Model.Matrix.Matrix;
import Model.Matrix.MatrixEnum;
import Model.Matrix.Utils.MatrixUtils;
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
        this.saveInStringHashmap(CommonParserHashKey.DECRYPT, (ArrayList<T>) data); // call submethod
    }

    /**
     * Solve all operation stacked in the hashmap one by one
     *
     * @param verbose true or false to print step by step
     */
    public void solve(boolean verbose) {
        // print summary
        this.solver.printSummaryOperations();
        // solve all inverses, call inverse solver
        CustomLogger.getInstance().addTitleLog("INVERSE OPERATIONS", verbose);
        this.model.getKeysArrayListHashMap().get(CommonParserHashKey.INVERSE).forEach(inverseInstruction -> this.solver.solveInversePool(inverseInstruction.toString(), verbose));
        // call determinant function
        CustomLogger.getInstance().addTitleLog("DETERMINANT OPERATIONS", verbose);
        this.model.getKeysArrayListHashMap().get(CommonParserHashKey.DETERMINANT).forEach(determinantInstruction -> this.solver.solveDeterminantPool(determinantInstruction.toString(), verbose));
        // call all rank declaration
        CustomLogger.getInstance().addTitleLog("RANK OPERATIONS", verbose);
        this.model.getKeysArrayListHashMap().get(CommonParserHashKey.RANK).forEach(rankInstruction -> this.solver.solveRankPool(rankInstruction.toString(), verbose));
        // execute all encrypts
        CustomLogger.getInstance().addTitleLog("ENCRYPT WORDS", verbose);
        this.model.getKeysArrayListHashMap().get(CommonParserHashKey.ENCRYPT).forEach(encryptInstruction -> this.solver.solveEncryptPool(encryptInstruction.toString(), verbose));
        // execute all decrypt
        CustomLogger.getInstance().addTitleLog("DECRYPT WORDS", verbose);
        this.model.getKeysArrayListHashMap().get(CommonParserHashKey.DECRYPT).forEach(decryptInstruction -> this.solver.solveDecryptPool((ArrayList<Double>) decryptInstruction, verbose));
        // execute all gauss jordan
        CustomLogger.getInstance().addTitleLog("GAUSS OPERATIONS", verbose);
        this.model.getKeysArrayListHashMap().get(CommonParserHashKey.GAUSS).forEach(gaussInstruction -> this.solver.solveGaussGJordanPool(gaussInstruction.toString(), verbose, CommonParserHashKey.JORDAN));
        // execute all gauss
        CustomLogger.getInstance().addTitleLog("GAUSS JORDAN OPERATIONS", verbose);
        this.model.getKeysArrayListHashMap().get(CommonParserHashKey.GAUSS).forEach(gaussInstruction -> this.solver.solveGaussGJordanPool(gaussInstruction.toString(), verbose, CommonParserHashKey.GAUSS));
        // execute all markov instances
        CustomLogger.getInstance().addTitleLog("MARKOV OPERATIONS", verbose);
        this.model.getKeysArrayListHashMap().get(CommonParserHashKey.MARKOV).forEach(markovOperation -> this.solver.solveMarkovPool((MarkovData) markovOperation, verbose));
        // arithmetical operations
        CustomLogger.getInstance().addTitleLog("ARITHMETICAL OPERATIONS", verbose);
        this.model.getKeysArrayListHashMap().get(CommonParserHashKey.ARITH_MATRIX).forEach(arithOperation -> this.solver.solveArithmeticalPool((NodeAVL<T>) arithOperation, verbose));
    }


}

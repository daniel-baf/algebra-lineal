package Model.Compiler;

import Model.Matrix.Matrix;

import java.util.ArrayList;
import java.util.HashMap;

public class ParserModel<T> {

    private final HashMap<String, Matrix> matrices; // matrix pool
    private final HashMap<CommonParserHashKey, ArrayList<T>> keysArrayListHashMap; // hashmap for common lists
    private final ArrayList<String[]> markovs; // list of markovs [matrix, iters
    // TODO implement arith AVL TREE


    public ParserModel() {
        this.matrices = new HashMap<>();
        this.keysArrayListHashMap = new HashMap<>();
        this.markovs = new ArrayList<>();
    }

    public HashMap<String, Matrix> getMatrices() {
        return matrices;
    }

    public HashMap<CommonParserHashKey, ArrayList<T>> getKeysArrayListHashMap() {
        return this.keysArrayListHashMap;
    }

    public ArrayList<String[]> getMarkovs() {
        return markovs;
    }

}


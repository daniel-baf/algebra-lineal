package Model.Compiler;

import Model.Matrix.Matrix;

import java.util.ArrayList;
import java.util.HashMap;

public class ParserModel<T> {

    private HashMap<String, Matrix> matrices; // matrix pool
    private HashMap<CommonParserHashKey, ArrayList<T>> keysArrayListHashMap; // hashmap for common lists
    private ArrayList<String[]> markovs; // list of markovs [matrix, iters
    // TODO implement arith AVL TREE


    public ParserModel() {
        this.matrices = new HashMap<>();
        this.keysArrayListHashMap = new HashMap<>();
        this.markovs = new ArrayList<>();
    }

    public HashMap<String, Matrix> getMatrices() {
        return matrices;
    }

    public void setMatrices(HashMap<String, Matrix> matrices) {
        this.matrices = matrices;
    }

    public HashMap<CommonParserHashKey, ArrayList<T>> getKeysArrayListHashMap() {
        return this.keysArrayListHashMap;
    }

    public void setKeysArrayListHashMap(HashMap<CommonParserHashKey, ArrayList<T>> keysArrayListHashMap) {
        this.keysArrayListHashMap = keysArrayListHashMap;
    }

    public ArrayList<String[]> getMarkovs() {
        return markovs;
    }

    public void setMarkovs(ArrayList<String[]> markovs) {
        this.markovs = markovs;
    }


}


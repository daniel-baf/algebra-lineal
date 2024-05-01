package Model.Compiler;

import Model.Matrix.Matrix;

import java.util.ArrayList;
import java.util.HashMap;

public class ParserModel {

    private HashMap<String, Matrix> matrices; // matrix pool
    private HashMap<CommonParserHashKey, ArrayList<String>> keysArrayListHashMap; // hashmap for common lists
    private ArrayList<double[]> decrypts; // list of numbers to decrypt
    private ArrayList<String[]> markovs; // list of markovs [matrix, iters
    // TODO implement arith AVL TREE


    public ParserModel() {
        this.matrices = new HashMap<>();
        this.keysArrayListHashMap = new HashMap<>();
        this.decrypts = new ArrayList<>();
        this.markovs = new ArrayList<>();
    }

    public HashMap<String, Matrix> getMatrices() {
        return matrices;
    }

    public void setMatrices(HashMap<String, Matrix> matrices) {
        this.matrices = matrices;
    }

    public HashMap<CommonParserHashKey, ArrayList<String>> getKeysArrayListHashMap() {
        return keysArrayListHashMap;
    }

    public void setKeysArrayListHashMap(HashMap<CommonParserHashKey, ArrayList<String>> keysArrayListHashMap) {
        this.keysArrayListHashMap = keysArrayListHashMap;
    }

    public ArrayList<double[]> getDecrypts() {
        return decrypts;
    }

    public void setDecrypts(ArrayList<double[]> decrypts) {
        this.decrypts = decrypts;
    }

    public ArrayList<String[]> getMarkovs() {
        return markovs;
    }

    public void setMarkovs(ArrayList<String[]> markovs) {
        this.markovs = markovs;
    }


}


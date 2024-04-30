package Model.Compiler;

import Model.Matrix.Matrix;

import java.util.ArrayList;
import java.util.HashMap;

public class ParserModel {

    private ArrayList<Matrix> matrices; // matrix pool
    private HashMap<CommonHashKeys, ArrayList<String>> hashes; // hashmap for common lists
    private ArrayList<double[]> decrypts; // list of numbers to decrypt
    private ArrayList<String[]> markovs; // list of markovs [matrix, iters
    // TODO implement arith AVL TREE


    public ParserModel() {
        this.matrices = new ArrayList<>();
        this.hashes = new HashMap<>();
        this.decrypts = new ArrayList<>();
        this.markovs = new ArrayList<>();
    }

    public ArrayList<Matrix> getMatrices() {
        return matrices;
    }

    public void setMatrices(ArrayList<Matrix> matrices) {
        this.matrices = matrices;
    }

    public HashMap<CommonHashKeys, ArrayList<String>> getHashes() {
        return hashes;
    }

    public void setHashes(HashMap<CommonHashKeys, ArrayList<String>> hashes) {
        this.hashes = hashes;
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

enum CommonHashKeys {
    INVERSE, DETERMINANT, RANK, ENCRYPT, GAUSS, JORDAN
}

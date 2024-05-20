package Model.Matrix.Solver;

import Domain.AVL.NodeAVL;
import Model.Matrix.Matrix;
import Model.Utils.CustomLogger;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Objects;


/**
 * This class gets the root node and solve the values by the matrix pool
 */
public class ArithAVLSolver<T> {

    private final HashMap<String, Matrix> matrixPool;

    public ArithAVLSolver(HashMap<String, Matrix> matrixPool) {
        this.matrixPool = matrixPool;
    }

    /**
     * Consume and return just the data from final AVL tree node
     *
     * @param rootNode node root to consume AVL tree
     * @return the final data
     */
    public Matrix solve(NodeAVL<T> rootNode) {
        return this.solve(rootNode, false);
    }

    /**
     * Consume and return just the data from final AVL tree node
     *
     * @param rootNode node root to consume AVL tree
     * @param verbose true or false to print step by step
     * @return the final data
     */
    public Matrix solve(NodeAVL<T> rootNode, boolean verbose) {
        NodeAVL<T> tmpData = this.solveAVLTree(rootNode, verbose);
        return Objects.isNull(tmpData) ? null : (Matrix) tmpData.getData();
    }

    /**
     * Solve the AVL and return just the final node after consume AVL tree
     *
     * @param rootNode the initial node of tree
     * @return the final node
     */
    private NodeAVL<T> solveAVLTree(NodeAVL<T> rootNode, boolean verbose) {
        // init with root node and check value
        this.configureMatrixNodes(rootNode);
        return this.solveArithmeticalExpression(rootNode, verbose);
    }

    /**
     * Configure the AVL, so IDS reference matrices from hashmpa
     *
     * @param currentNode AVL root node but with new DATA
     */
    public void configureMatrixNodes(NodeAVL<T> currentNode) {
        // check typeof node to search in matrices
        if (Objects.isNull(currentNode)) return; // no more recursive operations
        // Check if the data matches any of the enum values
        if (isPartOfEnum(currentNode)) {
            // T data is one of the enum values
            this.configureMatrixNodes(currentNode.getLeftChild());  // recursive call to configure calls
            this.configureMatrixNodes(currentNode.getRightChild()); // recursive call to configure matrix calls
        } else {
            currentNode.setData((T) this.matrixPool.get(currentNode.getData().toString())); // T data is not an enum
        }
    }

    /**
     * Execute arithmetical operations to an AVL tree of nodes with data OPERATION OR MATRIX
     *
     * @param currentNode the final node created from derivation
     * @return Final AVL node
     */
    private NodeAVL<T> solveArithmeticalExpression(NodeAVL<T> currentNode, boolean verbose) {
        if (Objects.isNull(currentNode)) return null; // no more recursive operations
        try {
            // Check if the data matches any of the enum values
            if (isPartOfEnum(currentNode)) {
                // T data is one of the enum values
                String enumOperation = currentNode.getData().toString();
                if (enumOperation.equals(ArithTokens.PARENTHESIS.getOperation())) {
                    return this.solveParenthesisOperation(currentNode, verbose);
                } else if (enumOperation.equals(ArithTokens.ADDITION.getOperation())) {
                    return this.solveArithOperationForMatrix(currentNode, ArithTokens.ADDITION, verbose);
                } else if (enumOperation.equals(ArithTokens.SUBTRACTION.getOperation())) {
                    return this.solveArithOperationForMatrix(currentNode, ArithTokens.SUBTRACTION, verbose);
                } else if (enumOperation.equals(ArithTokens.MULTIPLICATION.getOperation())) {
                    return this.solveArithOperationForMatrix(currentNode, ArithTokens.MULTIPLICATION, verbose);
                } else if (enumOperation.equals(ArithTokens.DIVISION.getOperation())) {
                    return this.solveArithOperationForMatrix(currentNode, ArithTokens.DIVISION, verbose);
                }
            }
            return currentNode; // matrix or number
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Unable to get value of current node at node solver " + e.getMessage(), true);
            return null;
        }
    }

    /**
     * Solve operation for parenthesis in AVL tree
     *
     * @param currentNode current node to check and update children
     * @return the new node with operation solved
     */
    private NodeAVL<T> solveParenthesisOperation(NodeAVL<T> currentNode, boolean verbose) {
        currentNode = currentNode.getLeftChild();
        return this.solveArithmeticalExpression(currentNode, verbose);
    }

    /**
     * Manage operations as sub or sum to matrices if both child are not null
     *
     * @param currentNode node to check and update
     * @param operation   the operation to exec +, -, *, /
     * @return the new node
     */
    private NodeAVL<T> solveArithOperationForMatrix(NodeAVL<T> currentNode, ArithTokens operation, boolean verbose) {
        // return the sum of left and right children
        if (Objects.isNull(currentNode.getLeftChild()) || Objects.isNull(currentNode.getRightChild())) {
            return null;
        }
        Matrix leftMatrix = (Matrix) Objects.requireNonNull(this.solveArithmeticalExpression(currentNode.getLeftChild(), verbose)).getData();
        Matrix rightMatrix = (Matrix) Objects.requireNonNull(this.solveArithmeticalExpression(currentNode.getRightChild(), verbose)).getData();
        currentNode.setLeftChild(null); // remove child references
        currentNode.setRightChild(null);
        if (!Objects.isNull(leftMatrix) && !Objects.isNull(rightMatrix)) {
            Matrix result = switch (operation) {
                case ADDITION -> leftMatrix.add(rightMatrix, verbose);
                case SUBTRACTION -> leftMatrix.sub(rightMatrix, verbose);
                case MULTIPLICATION -> leftMatrix.multiply(rightMatrix, verbose);
                case DIVISION -> leftMatrix.div(rightMatrix, verbose);
                default -> null; // no operation managed by this function
            };
            currentNode.setData((T) result);
            return currentNode;
        } else {
            return null;
        }
    }

    /**
     * Check if data in a node is part of the enum of possible operations AKA no matrix node
     *
     * @param node current node to check
     * @return true or false if data is part of enum
     */
    private boolean isPartOfEnum(NodeAVL<T> node) {
        String checkValue = node.getData().toString();
        return EnumSet.allOf(ArithTokens.class).stream().anyMatch(arithTokens -> arithTokens.getOperation().equals(checkValue));
    }
}

enum ArithTokens {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    PARENTHESIS("()");

    private final String operation;

    // Constructor
    ArithTokens(String operation) {
        this.operation = operation;
    }

    // Getter method to retrieve the display name
    public String getOperation() {
        return operation;
    }
}

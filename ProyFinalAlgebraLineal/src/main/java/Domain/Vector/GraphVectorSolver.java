package Domain.Vector;

import Domain.AVL.NodeAVL;
import Model.Utils.CustomLogger;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class GraphVectorSolver<T> {

    private final HashMap<String, GraphVector> vectors;
    private final GraphVectorOperator operator;

    public GraphVectorSolver(HashMap<String, GraphVector> vectors) {
        this.vectors = vectors;
        this.operator = new GraphVectorOperator();
    }

    /**
     * Pre-configure all vectors so all operations are expected well formated
     *
     * @param vector  vector to configure
     * @param verbose true or false to display step by step
     */
    public void preSolve(GraphVector vector, boolean verbose) {
        this.operator.preSolve(vector, verbose);
    }


    /**
     * Solve an AVL tree using operations for vectors
     *
     * @param rootNode rootNode, main node for operations
     * @param verbose  true or false
     * @return the result, null if any error occur
     */
    public T solve(NodeAVL<T> rootNode, boolean verbose) {
        NodeAVL<T> tmpData = this.solveAVLTree(rootNode, verbose);
        return Objects.isNull(tmpData) ? null : tmpData.getData();
    }

    public NodeAVL<T> solveAVLTree(NodeAVL<T> currentNode, boolean verbose) {
        this.configureVectorNodes(currentNode);
        return this.solveArithmeticalExpressionVectors(currentNode, verbose);
    }

    /**
     * Configure the AVL, so IDS reference matrices from hashmap
     *
     * @param currentNode AVL root node but with new DATA
     */
    @SuppressWarnings("Unchecked")
    public void configureVectorNodes(NodeAVL<T> currentNode) {
        // check typeof node to search in VECTORS
        if (Objects.isNull(currentNode)) return; // no more recursive operations
        // Check if the data matches any of the enum values
        if (isPartOfEnum(currentNode)) {
            // T data is one of the enum values
            this.configureVectorNodes(currentNode.getLeftChild());  // recursive call to configure calls
            this.configureVectorNodes(currentNode.getRightChild()); // recursive call to configure vector calls
        } else {
            Object data = currentNode.getData() instanceof Double ?
                    new GraphVectorBuilder().setName(currentNode.getData().toString())
                            .setLength(Double.parseDouble(currentNode.getData().toString()))
                            .build()
                    : this.vectors.get(currentNode.getData().toString());
            currentNode.setData((T) data);
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

    /**
     * Solve operation for parenthesis in AVL tree
     *
     * @param currentNode current node to check and update children
     * @return the new node with operation solved
     */
    private NodeAVL<T> solveParenthesisOperation(NodeAVL<T> currentNode, boolean verbose) {
        currentNode = currentNode.getLeftChild();
        return this.solveArithmeticalExpressionVectors(currentNode, verbose);
    }

    /**
     * Manage operations as sub or sum to matrices if both child are not null
     *
     * @param currentNode node to check and update
     * @param operation   the operation to exec +, -, *, /
     * @return the new node
     */
    private NodeAVL<T> consumeArithOperationsParserOperator(NodeAVL<T> currentNode, ArithTokens operation, boolean verbose) {
        // return the sum of left and right children
        if (Objects.isNull(currentNode.getLeftChild()) || Objects.isNull(currentNode.getRightChild())) {
            return null;
        }
        GraphVector leftVector = (GraphVector) Objects.requireNonNull(this.solveArithmeticalExpressionVectors(currentNode.getLeftChild(), verbose)).getData();
        GraphVector rightVector = (GraphVector) Objects.requireNonNull(this.solveArithmeticalExpressionVectors(currentNode.getRightChild(), verbose)).getData();
        currentNode.setLeftChild(null); // remove child references
        currentNode.setRightChild(null);
        if (!Objects.isNull(leftVector) && !Objects.isNull(rightVector)) {
            GraphVector result = switch (operation) {
                case ADDITION -> this.operator.add(leftVector, rightVector, verbose);
                case SUBTRACTION -> this.operator.sub(leftVector, rightVector, verbose);
                case MULTIPLICATION -> this.operator.multiply(leftVector, rightVector, verbose);
                case DOT -> this.operator.dotProduct(leftVector, rightVector, verbose);
                default -> null; // no operation managed by this function
            };
            currentNode.setData((T) result);
            return currentNode;
        } else {
            return null;
        }
    }

    /**
     * Solve operations for vectors
     * @param currentNode node to check in current step
     * @param verbose true or false to print step by step
     * @return final result
     */
    private NodeAVL<T> solveArithmeticalExpressionVectors(NodeAVL<T> currentNode, boolean verbose) {
        if (Objects.isNull(currentNode)) return null; // no more recursive operations
        try {
            // Check if the data matches any of the enum values
            if (isPartOfEnum(currentNode)) {
                // T data is one of the enum values
                String enumOperation = currentNode.getData().toString();
                if (enumOperation.equals(ArithTokens.PARENTHESIS.getOperation())) {
                    return this.solveParenthesisOperation(currentNode, verbose);
                } else if (enumOperation.equals(ArithTokens.ADDITION.getOperation())) {
                    return this.consumeArithOperationsParserOperator(currentNode, ArithTokens.ADDITION, verbose);
                } else if (enumOperation.equals(ArithTokens.SUBTRACTION.getOperation())) {
                    return this.consumeArithOperationsParserOperator(currentNode, ArithTokens.SUBTRACTION, verbose);
                } else if (enumOperation.equals(ArithTokens.MULTIPLICATION.getOperation())) {
                    return this.consumeArithOperationsParserOperator(currentNode, ArithTokens.MULTIPLICATION, verbose);
                } else if (enumOperation.equals(ArithTokens.DOT.getOperation())) {
                    return this.consumeArithOperationsParserOperator(currentNode, ArithTokens.DOT, verbose);
                }
            }
            return currentNode; // matrix or number
        } catch (Exception e) {
            CustomLogger.getInstance().addLog("Unable to get value of current node at node solver " + e.getMessage(), true);
            return null;
        }
    }
}

enum ArithTokens {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DOT("."),
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

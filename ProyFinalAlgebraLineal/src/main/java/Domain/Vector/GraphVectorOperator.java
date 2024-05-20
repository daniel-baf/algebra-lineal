package Domain.Vector;

import Model.Utils.CustomLogger;

import java.util.Objects;

public class GraphVectorOperator {


    /**
     * SUM VALUES FOR VECTORS
     *
     * @param leftVector  left vector to operate
     * @param rightVector righ tvector to operate
     * @param verbose     true or false print step by step
     * @return the new value of the sum
     */
    public GraphVector add(GraphVector leftVector, GraphVector rightVector, boolean verbose) {
        // both not the same, check if operable
        if ((leftVector.getQuadrant() != CartesianQuadrant.SCALAR && rightVector.getQuadrant() == CartesianQuadrant.SCALAR) ||
                (leftVector.getQuadrant() == CartesianQuadrant.SCALAR && rightVector.getQuadrant() != CartesianQuadrant.SCALAR)) {
            CustomLogger.getInstance().addLog("\tUnable to operate different types, escalar and vector not summable {"
                    + leftVector.getQuadrant() + ", " + rightVector.getQuadrant() + " }", true);
            return leftVector;
        }
        try {
            GraphVector resultVector = (GraphVector) leftVector.clone();
            // both are scalar
            if (leftVector.getQuadrant().equals(CartesianQuadrant.SCALAR)) {
                resultVector.setLength(resultVector.getLength() + rightVector.getLength());
                CustomLogger.getInstance().addLog(String.format("\ttOperation for scalars, both scalar, just add left(%1$f) + right(%2$f) = %3$f", leftVector.getLength(), rightVector.getLength(), resultVector.getLength()), verbose);
            }
            resultVector.setName(String.format("(%1$s %2$s %3$s)", leftVector.getName(), ArithTokens.ADDITION.getOperation(), rightVector.getName()));
            // both are vectors
            resultVector.setEndPoint(new NodeVector(leftVector.getEndPoint().getX() + rightVector.getXComponent(),
                    leftVector.getEndPoint().getY() + rightVector.getYComponent()));
            CustomLogger.getInstance().addLog(String.format("\tBoth are vectors, origin = %1$s\n\t\tdestination(x) = EndNodeLeft(x) + ComponentRight(x)"
                            + "\n\t\tdestination(x) = %1$f + %2$f = %3$f"
                            + "\n\t\tdestination(y) = EndNodeLeft(y) + ComponentRight(Y)"
                            + "\n\t\tdestination(y) = %4$f + %5$f = %6$f",
                    leftVector.getEndPoint().getX(), rightVector.getXComponent(), resultVector.getEndPoint().getX(),
                    leftVector.getEndPoint().getY(), rightVector.getYComponent(), resultVector.getEndPoint().getY()), verbose);
            this.preSolve(resultVector, verbose);
            return resultVector;
        } catch (CloneNotSupportedException e) {
            CustomLogger.getInstance().addLog("UNABLE TO SUBTRACT ADITION " + e.getMessage(), true);
            return leftVector;
        }
    }

    /**
     * Substract two vectores, both must be a non scalar value of instance
     *
     * @param leftVector  the left vector to operate
     * @param rightVector the right vector to operate
     * @param verbose     true or false to print step by step
     * @return the result
     */
    public GraphVector sub(GraphVector leftVector, GraphVector rightVector, boolean verbose) {
        // both not the same, check if operable
        if ((leftVector.getQuadrant() != CartesianQuadrant.SCALAR && rightVector.getQuadrant() == CartesianQuadrant.SCALAR) ||
                (leftVector.getQuadrant() == CartesianQuadrant.SCALAR && rightVector.getQuadrant() != CartesianQuadrant.SCALAR)) {
            CustomLogger.getInstance().addLog("\tUnable to operate different types, escalar and vector not able to subtract {"
                    + leftVector.getQuadrant() + ", " + rightVector.getQuadrant() + " }", true);
            return leftVector;
        }
        // both are scalar
        try {
            GraphVector resultVector = (GraphVector) leftVector.clone();
            if (leftVector.getQuadrant().equals(CartesianQuadrant.SCALAR)) {
                resultVector.setLength(resultVector.getLength() - rightVector.getLength());
                CustomLogger.getInstance().addLog(String.format("\ttOperation for scalars, both scalar, just add left(%1$f) - right(%2$f) = %3$f", leftVector.getLength(), rightVector.getLength(), resultVector.getLength()), verbose);
            }
            resultVector.setName(String.format("(%1$s %2$s %3$s)", leftVector.getName(), ArithTokens.SUBTRACTION.getOperation(), rightVector.getName()));
            // both are vectors
            resultVector.setEndPoint(new NodeVector(leftVector.getEndPoint().getX() - rightVector.getXComponent(),
                    leftVector.getEndPoint().getY() - rightVector.getYComponent()));
            CustomLogger.getInstance().addLog(String.format("\tBoth are vectors, origin = %1$s\n\t\tdestination(x) = EndNodeLeft(x) - ComponentRight(x)"
                            + "\n\t\tdestination(x) = %1$f - %2$f = %3$f"
                            + "\n\t\tdestination(y) = EndNodeLeft(y) - ComponentRight(Y)"
                            + "\n\t\tdestination(y) = %4$f - %5$f = %6$f",
                    leftVector.getEndPoint().getX(), rightVector.getXComponent(), resultVector.getEndPoint().getX(),
                    leftVector.getEndPoint().getY(), rightVector.getYComponent(), resultVector.getEndPoint().getY()), verbose);
            this.preSolve(resultVector, verbose);
            return resultVector;
        } catch (CloneNotSupportedException e) {
            CustomLogger.getInstance().addLog("UNABLE TO SUBTRACT OPERATION " + e.getMessage(), true);
            return leftVector;
        }
    }

    /**
     * Multiply VALUES, same, scalar or not scalar, 1st check if the values are scalar, if right is scalar, swap it and call function again, if left is scalar,
     * multiply the new end point and call function to autfill new values and return the value
     * the alternative case, is when both are vectors, so applies a cross multiply
     *
     * @param leftVector  the left data
     * @param rightVector the rigth vector to multiply
     * @param verbose     true or false to print step by step
     * @return the result
     */
    public GraphVector multiply(GraphVector leftVector, GraphVector rightVector, boolean verbose) {
        // any is scalar
        if (leftVector.getQuadrant() != CartesianQuadrant.SCALAR && rightVector.getQuadrant() == CartesianQuadrant.SCALAR) {
            return this.multiply(rightVector, leftVector, verbose);
        }
        if (leftVector.getQuadrant() == CartesianQuadrant.SCALAR && rightVector.getQuadrant() != CartesianQuadrant.SCALAR) {
            try {
                GraphVector copyVector = (GraphVector) rightVector.clone();
                copyVector.setName(String.format("(%1$f * %2$s)", leftVector.getLength(), rightVector.getName()));
                copyVector.setEndPoint(new NodeVector(copyVector.getEndPoint().getX() * leftVector.getLength(), copyVector.getEndPoint().getY() * leftVector.getLength()));
                return copyVector;
            } catch (Exception e) {
                CustomLogger.getInstance().addLog("\tUNABLE TO MULTIPLY VECTOR AND SCALAR OPERATION " + e.getMessage(), true);
                return rightVector;
            }
        }
        // both are vectors
        CustomLogger.getInstance().addLog("\tTHIS IS NOT IMPLEMENTED", true);
        return leftVector;
    }

    /**
     * This function execute dot multiplication between 2 vectors
     *
     * @param leftVector  left vector, can be scalar, but will call other function
     * @param rightVector right vector, can be scalar, but will call other function
     * @param verbose     true or false to step by step
     * @return the result vector
     */
    public GraphVector dotProduct(GraphVector leftVector, GraphVector rightVector, boolean verbose) {
        // any is scalar
        if (leftVector.getQuadrant() == CartesianQuadrant.SCALAR || rightVector.getQuadrant() == CartesianQuadrant.SCALAR) {
            return this.multiply(rightVector, leftVector, verbose);
        }
        // both are vectors
        double result = leftVector.getXComponent() * rightVector.getXComponent() + leftVector.getYComponent() * rightVector.getYComponent();
        // Log the individual multiplications
        CustomLogger.getInstance().addLog(String.format("Multiply components X + components Y:\n\tleftX = %1$f, rightX = %2$f\n\tlefty = %3$f, righty = %4$f\n\t%1$f * %2$f + %3$f * %4$f = %5$f",
                leftVector.getXComponent(), rightVector.getXComponent(), leftVector.getYComponent(), rightVector.getYComponent(), result), verbose);
        return new GraphVectorBuilder().setName("SCALAR").setLength(result).setQuadrant(CartesianQuadrant.SCALAR).build();
    }

    /**
     * Pre-configure all vectors so all operations are expected well formated
     *
     * @param vector  vector to configure
     * @param verbose true or false to display step by step
     */
    public void preSolve(GraphVector vector, boolean verbose) {
        // check components
        this.fillByAngle(vector, verbose);
        this.fillByNodes(vector, verbose);
    }

    /**
     * Fill the vector using only nodes as parameters
     *
     * @param vector  the vector to check
     * @param verbose true or false to validate step by step
     */
    public void fillByNodes(GraphVector vector, boolean verbose) {
        if (Objects.isNull(vector.getOriginPoint()) || Objects.isNull(vector.getEndPoint())) {
            return;  // continue instruction, try to fill angle if any data from this method is needed
        }
        CustomLogger.getInstance().addLog(String.format("Solve length for vector %1$s ", vector.getName()), verbose);
        CustomLogger.getInstance().addLog(String.format("\tL(%1$s)^2 =  (End(x) - Origin(x))^2 + (End(y) - Origin(y))^2", vector.getName()), verbose);
        vector.setLength(Math.sqrt(Math.pow((vector.getEndPoint().getX() - vector.getOriginPoint().getX()), 2) +
                Math.pow((vector.getEndPoint().getY() - vector.getOriginPoint().getY()), 2)));
        CustomLogger.getInstance().addLog(String.format("\tL(%1$s)^2 =  (%2$f - %3$f)^2 + (%4$f - %5$f)^2^2 -> L(%1$s) = %6$f",
                vector.getName(), vector.getEndPoint().getX(), vector.getOriginPoint().getX(), vector.getEndPoint().getY(), vector.getOriginPoint().getX(), vector.getLength()), verbose);
        // set components by splice
        vector.setXComponent(vector.getEndPoint().getX() - vector.getOriginPoint().getX());
        vector.setYComponent(vector.getEndPoint().getY() - vector.getOriginPoint().getY());
        CustomLogger.getInstance().addLog(String.format("\tGetting components\n\t\tx = End(x) - Origin(x) -> %1$f - %2$f = %3$f \n\t\ty= End(y) - Origin(y)  -> %4$f - %5$f = %6$f",
                vector.getEndPoint().getX(), vector.getOriginPoint().getX(), vector.getXComponent(), vector.getEndPoint().getY(), vector.getOriginPoint().getY(), vector.getYComponent()), verbose);
        // get angle
        vector.calculateCuadrant(verbose);
        vector.calculateAngle(verbose);
        CustomLogger.getInstance().addSectionEnd(3, verbose);
    }

    /**
     * Fill all data from a vector using the origin of angle and length     \
     *
     * @param vector  vector initial to modify
     * @param verbose true or false to print step by step
     */
    public void fillByAngle(GraphVector vector, boolean verbose) {
        // check if origin point is empty
        if (!Objects.isNull(vector.getEndPoint())) { // no validation
            return;
        }
        if (Objects.isNull(vector.getOriginPoint())) {
            vector.setOriginPoint(new NodeVector(0, 0));
            CustomLogger.getInstance().addLog("Creating new origin node to [0,0]", verbose);
        }
        // calculate components
        double x = vector.getLength() * (Math.cos(vector.getAngle() * Math.PI / 180));
        double y = vector.getLength() * (Math.sin(vector.getAngle() * Math.PI / 180));
        // create new end point using origin + components
        NodeVector newEndPoint = new NodeVector(x + vector.getOriginPoint().getX(), y + vector.getOriginPoint().getY());
        vector.setEndPoint(newEndPoint);
        CustomLogger.getInstance().addLog(String.format("Pre generate end point for vector %1$s\n\t\ttmp(x) = %2$f * cos(%3$f) + %4$f\n\t\ttmp(y) = %2$f * cos(%3$f) + %5$f",
                vector.getName(), vector.getLength(), vector.getAngle(), vector.getOriginPoint().getX(), vector.getOriginPoint().getY()), verbose);
    }

}

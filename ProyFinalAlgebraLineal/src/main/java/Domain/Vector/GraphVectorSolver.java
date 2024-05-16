package Domain.Vector;

import Model.Utils.CustomLogger;

import java.util.Objects;

public class GraphVectorSolver {

    public void solve(GraphVector vector, boolean verbose) {
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
        vector.setLength(Math.sqrt(Math.pow((vector.getEndPoint().getX() - vector.getOriginPoint().getX()), 2) + Math.pow((vector.getEndPoint().getY() - vector.getOriginPoint().getY()), 2)));
        CustomLogger.getInstance().addLog(String.format("\tL(%1$s)^2 =  (%2$f - %3$f)^2 + (%4$f - %5$f)^2^2 -> L(%1$s) = %6$f", vector.getName(), vector.getEndPoint().getX(), vector.getOriginPoint().getX(), vector.getEndPoint().getY(), vector.getOriginPoint().getX(), vector.getLength()), verbose);
        // set components by splice
        vector.setXComponent(vector.getEndPoint().getX() - vector.getOriginPoint().getX());
        vector.setYComponent(vector.getEndPoint().getY() - vector.getOriginPoint().getY());
        CustomLogger.getInstance().addLog(String.format("\tGetting components\n\t\tx = End(x) - Origin(x) -> %1$f - %2$f = %3$f \n\t\ty= End(y) - Origin(y)  -> %4$f - %5$f = %6$f", vector.getEndPoint().getX(), vector.getOriginPoint().getX(), vector.getXComponent(), vector.getEndPoint().getY(), vector.getOriginPoint().getY(), vector.getYComponent()), verbose);
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

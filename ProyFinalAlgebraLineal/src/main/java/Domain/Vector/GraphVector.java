package Domain.Vector;

import Model.Utils.CustomLogger;

public class GraphVector {
    private NodeVector originPoint;
    private NodeVector endPoint;
    private double xComponent;
    private double yComponent;
    private double angle;
    private double length;
    private String name;
    private CartesianQuadrant quadrant;

    protected GraphVector(GraphVectorBuilder builder) {
        this.originPoint = builder.getOriginPoint();
        this.endPoint = builder.getEndPoint();
        this.xComponent = builder.getxComponent();
        this.yComponent = builder.getyComponent();
        this.angle = builder.getAngle();
        this.length = builder.getLength();
        this.name = builder.getName();
        this.quadrant = builder.getQuadrant();
    }

    public NodeVector getOriginPoint() {
        return originPoint;
    }

    public NodeVector getEndPoint() {
        return endPoint;
    }

    public double getXComponent() {
        return xComponent;
    }

    public double getYComponent() {
        return yComponent;
    }

    public double getAngle() {
        return angle;
    }

    public double getLength() {
        return length;
    }

    public String getName() {
        return this.name;
    }


    public void setOriginPoint(NodeVector originPoint) {
        this.originPoint = originPoint;
    }

    public void setEndPoint(NodeVector endPoint) {
        this.endPoint = endPoint;
    }

    public void setXComponent(double xComponent) {
        this.xComponent = xComponent;
    }

    public void setYComponent(double yComponent) {
        this.yComponent = yComponent;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Estimate the current quadrant using the components
     *
     * @param verbose true or false to print result
     */
    public void calculateCuadrant(boolean verbose) {
        if (this.xComponent < 0) {
            this.quadrant = this.yComponent < 0 ? CartesianQuadrant.THREE : CartesianQuadrant.TWO;
        } else {
            this.quadrant = this.yComponent < 0 ? CartesianQuadrant.FOUR : CartesianQuadrant.ONE;
        }
        CustomLogger.getInstance().addLog("\t\t\tX and Y component ends at quadrant " + this.quadrant, verbose);
    }

    /**
     * Calculate the angle of vector using nodes, this is using the circle, 1st check if any is 0, then check direction,
     * if not match, then execute atan of abs values and then transform the relative angle to a global using 360and
     * 180-as reference to sum or sub the value
     *
     * @param verbose true or false to display data
     */
    public void calculateAngle(boolean verbose) {
        // check if x is 0
        if (this.xComponent == 0 && this.yComponent != 0) {
            this.angle = this.yComponent < 0 ? -270 : 90;
            CustomLogger.getInstance().addLog("\tAvoiding component y, is 0, check direction + or - -> angle is: " + this.angle, verbose);
            return;
        }
        if (this.xComponent != 0 && this.yComponent == 0) {
            this.angle = this.xComponent < 0 ? -180 : 0;
            CustomLogger.getInstance().addLog("\tAvoiding component x, is 0, check direction + or - -> angle is: " + this.angle, verbose);
            return;
        }
        this.angle = Math.atan(Math.abs(this.yComponent) / Math.abs(this.xComponent));
        CustomLogger.getInstance().addLog(String.format("\tCalculate angle using components\n\t\tRelative angle = arc_tan(y/x) -> arc_tan(%1$f/%2$f) = %3$f", Math.abs(this.yComponent), Math.abs(this.xComponent), this.angle), verbose);
        this.angle = this.angle * 180 / Math.PI;
        CustomLogger.getInstance().addLog("\t\tCasting radians to degrees -> angle rad * 180 / pi = " + this.angle, verbose);
        this.angle = this.quadrant == CartesianQuadrant.ONE ? this.angle : this.quadrant == CartesianQuadrant.TWO ? 180 - this.angle : this.quadrant == CartesianQuadrant.THREE ? 180 + this.angle : 360 - this.angle;
        CustomLogger.getInstance().addLog("\t\tFixing relative angle to global angle from 0 degrees, new angle is " + this.angle, verbose);
    }

    @Override
    public String toString() {
        return "GraphVector{" + "name=" + this.name + ", originPoint=" + originPoint + ", endPoint=" + endPoint + ", xComponent=" + xComponent + ", yComponent=" + yComponent + ", angle=" + angle + ", length=" + length + '}';
    }
}

enum CartesianQuadrant {
    ONE, TWO, THREE, FOUR, NOT_OPERABLE
}

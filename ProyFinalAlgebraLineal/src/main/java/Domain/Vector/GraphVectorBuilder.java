package Domain.Vector;

public class GraphVectorBuilder {
    private NodeVector originPoint;
    private NodeVector endPoint;
    private double xComponent;
    private double yComponent;
    private double angle;
    private double length;

    public GraphVectorBuilder() {
        // Set default values if needed
    }

    public GraphVector build() {
        return new GraphVector(this);
    }

    public GraphVectorBuilder setOriginPoint(NodeVector originPoint) {
        this.originPoint = originPoint;
        return this;
    }


    public GraphVectorBuilder setEndPoint(NodeVector endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public GraphVectorBuilder setXComponent(double xComponent) {
        this.xComponent = xComponent;
        return this;
    }


    public GraphVectorBuilder setYComponent(double yComponent) {
        this.yComponent = yComponent;
        return this;
    }

    public GraphVectorBuilder setAngle(double angle) {
        this.angle = angle;
        return this;
    }

    public GraphVectorBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    public NodeVector getOriginPoint() {
        return originPoint;
    }

    public NodeVector getEndPoint() {
        return endPoint;
    }

    public double getxComponent() {
        return xComponent;
    }

    public double getyComponent() {
        return yComponent;
    }

    public double getAngle() {
        return angle;
    }

    public double getLength() {
        return length;
    }
}

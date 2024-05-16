package Domain.Vector;

public class GraphVector {
    private final NodeVector originPoint;
    private final NodeVector endPoint;
    private final double xComponent;
    private final double yComponent;
    private final double angle;
    private final double length;
    private final String name;

    protected GraphVector(GraphVectorBuilder builder) {
        this.originPoint = builder.getOriginPoint();
        this.endPoint = builder.getEndPoint();
        this.xComponent = builder.getxComponent();
        this.yComponent = builder.getyComponent();
        this.angle = builder.getAngle();
        this.length = builder.getLength();
        this.name = builder.getName();
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

    @Override
    public String toString() {
        return "GraphVector{" +
                "name=" + this.name +
                ", originPoint=" + originPoint +
                ", endPoint=" + endPoint +
                ", xComponent=" + xComponent +
                ", yComponent=" + yComponent +
                ", angle=" + angle +
                ", length=" + length +
                '}';
    }
}

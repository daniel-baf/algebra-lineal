package Domain.Vector;

public class NodeVector implements Cloneable {
    private double x;
    private double y;

    public NodeVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "NodeVector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public NodeVector clone() {
        try {
            NodeVector clone = (NodeVector) super.clone();
            clone.setX(this.x);
            clone.setY(this.y);
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
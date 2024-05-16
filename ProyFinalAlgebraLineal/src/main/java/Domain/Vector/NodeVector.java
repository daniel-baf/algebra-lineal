package Domain.Vector;

public class NodeVector {
    private double x;
    private double y;

    public NodeVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "NodeVector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
package Domain.AVL;

import java.util.Objects;

public class NodeAVL<T> {
    private NodeAVL<T> parent;
    private NodeAVL<T> leftChild;
    private NodeAVL<T> rightChild;
    private T data;

    // Private constructor to prevent direct instantiation
    public NodeAVL(NodeAVLBuilder<T> builder) {
        this.parent = builder.getParent();
        this.leftChild = builder.getLeftChild();
        this.rightChild = builder.getRightChild();
        this.data = builder.getData();
    }

    // Other methods and fields remain unchanged
    @Override
    public String toString() {
        String leftString = Objects.isNull(this.leftChild)? "NULL": this.leftChild.toString();
        String rightString = Objects.isNull(this.rightChild)? "NULL": this.rightChild.toString();
        return String.format("Node %1$s [: {left: {%2$s} right: {%3$s}" , this.data, leftString, rightString);
    }

    public String getPrintable() {
        return (!Objects.isNull(this.leftChild) ? this.leftChild.getPrintable() : "") + this + (!Objects.isNull(this.rightChild) ? this.rightChild.getPrintable() : "") + "\n";
    }

    public NodeAVL<T> getParent() {
        return parent;
    }

    public void setParent(NodeAVL<T> parent) {
        this.parent = parent;
    }

    public NodeAVL<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(NodeAVL<T> leftChild) {
        this.leftChild = leftChild;
    }

    public NodeAVL<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(NodeAVL<T> rightChild) {
        this.rightChild = rightChild;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

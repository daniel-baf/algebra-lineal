package Domain.AVL;

public class NodeAVLBuilder<T> {
    private NodeAVL<T> parent;
    private NodeAVL<T> leftChild;
    private NodeAVL<T> rightChild;
    private T data;

    public NodeAVLBuilder() {
        // Initialize default values here if needed
    }

    public NodeAVLBuilder<T> setParent(NodeAVL<T> parent) {
        this.parent = parent;
        return this;
    }

    public NodeAVLBuilder<T> setLeftChild(NodeAVL<T> leftChild) {
        this.leftChild = leftChild;
        return this;
    }

    public NodeAVLBuilder<T> setRightChild(NodeAVL<T> rightChild) {
        this.rightChild = rightChild;
        return this;
    }

    public NodeAVLBuilder<T> setData(T data) {
        this.data = data;
        return this;
    }

    public NodeAVL<T> getParent() {
        return parent;
    }

    public NodeAVL<T> getLeftChild() {
        return leftChild;
    }

    public NodeAVL<T> getRightChild() {
        return rightChild;
    }

    public T getData() {
        return data;
    }

    public NodeAVL<T> build() {
        return  new NodeAVL<>(this);
    }
}

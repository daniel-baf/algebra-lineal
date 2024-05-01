package Domain.Markov;

/**
 * Generic class to save the values of token to future operations
 */
public class MarkovData {

    private String matrix;
    private String status;
    private int iterations;

    public MarkovData(String matrix, String status, int iterations) {
        this.matrix = matrix;
        this.status = status;
        this.iterations = iterations;
    }

    public String getMatrix() {
        return matrix;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public String toString() {
        return "MarkovData{" +
                "matrix='" + matrix + '\'' +
                ", status='" + status + '\'' +
                ", iterations=" + iterations +
                '}';
    }
}

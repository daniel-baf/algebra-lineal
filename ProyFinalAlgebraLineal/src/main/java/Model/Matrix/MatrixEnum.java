package Model.Matrix;

public enum MatrixEnum {
    ROWS_SHAPE("Rows"),
    COLUMNS_SHAPE("Columns");

    private final String value;

    MatrixEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

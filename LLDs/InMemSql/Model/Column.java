package Model;

public class Column {
    private String columnName;
    public Constraint constraint;
    public Boolean hasConstraint;

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }
}
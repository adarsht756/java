package Model;
public class StringColumn extends Column {
    public Constraint constraint;
    public Boolean hasConstraint;

    public StringColumn(String columnName, Constraint constraint) {
        super.setColumnName(columnName);
        if (constraint != null) {
            super.hasConstraint = true;
            super.constraint = constraint;
        } else super.hasConstraint = false;
    }
}
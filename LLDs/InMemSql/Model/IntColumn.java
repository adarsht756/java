package Model;
public class IntColumn extends Column {
    public IntColumn(String columnName, Constraint constraint) {
        super.setColumnName(columnName);
        if (constraint != null) {
            super.hasConstraint = true;
            super.constraint = constraint;
        } else super.hasConstraint = false;
    }
}
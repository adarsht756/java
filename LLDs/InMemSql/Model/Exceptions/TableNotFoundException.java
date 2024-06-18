package Model.Exceptions;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String tableName) {
        super("Table " + tableName + " not found");
    }
}
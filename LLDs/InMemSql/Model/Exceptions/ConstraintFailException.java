package Model.Exceptions;

public class ConstraintFailException extends RuntimeException {
    public ConstraintFailException(String entityName) {
        super("Entered " + entityName + " is invalid. ");
    }
}
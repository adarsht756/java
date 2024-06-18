package Model.Exceptions;

public class CreateRecordException extends RuntimeException {
    public CreateRecordException() {
        super("Error occured while creating record");
    }
}
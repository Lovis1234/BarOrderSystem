package nl.belastingdienst.barordersystem.Exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
        super("Record not found error occurred");
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}

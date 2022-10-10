package nl.belastingdienst.barordersystem.Exceptions;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException() {
        super("custom error thrown");
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}

package nl.belastingdienst.barordersystem.Exceptions;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException() {
        super("Record not found error occured");
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}

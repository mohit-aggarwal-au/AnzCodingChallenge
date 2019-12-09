package au.com.anz.fxcalculator.exception;

public class InvalidInputFileException extends RuntimeException {
    public InvalidInputFileException(String errorMessage) {
        super(errorMessage);
    }
}

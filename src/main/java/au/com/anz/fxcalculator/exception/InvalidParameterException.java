package au.com.anz.fxcalculator.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String errorMessage) {
        super(errorMessage);
    }
}

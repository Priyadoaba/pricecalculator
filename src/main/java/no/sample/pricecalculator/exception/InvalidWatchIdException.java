package no.sample.pricecalculator.exception;

public class InvalidWatchIdException extends ApplicationException {

    public InvalidWatchIdException(String errorMessage) {
        super(errorMessage);
    }

    public String getErrorCode() {
        return "invalid";
    }
}

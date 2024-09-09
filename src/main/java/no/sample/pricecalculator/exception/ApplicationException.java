package no.sample.pricecalculator.exception;

public abstract class ApplicationException extends RuntimeException {

    private final String errorMessage;

    ApplicationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public abstract String getErrorCode();

    public String getErrorMessage() {
        return this.errorMessage;
    }
}

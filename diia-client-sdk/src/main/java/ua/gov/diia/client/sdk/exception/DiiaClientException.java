package ua.gov.diia.client.sdk.exception;

import ua.gov.diia.client.sdk.remote.model.ErrorMessage;

public class DiiaClientException extends RuntimeException {
    private ErrorMessage errorMessage;

    public DiiaClientException() {
    }

    public DiiaClientException(String message) {
        super(message);
    }

    public DiiaClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiiaClientException(Throwable cause) {
        super(cause);
    }

    public DiiaClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DiiaClientException(ErrorMessage errorMessage) {
        super(errorMessage.toString());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}

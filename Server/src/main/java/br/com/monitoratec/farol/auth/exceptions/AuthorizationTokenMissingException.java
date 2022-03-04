package br.com.monitoratec.farol.auth.exceptions;

public class AuthorizationTokenMissingException extends Exception {

    public AuthorizationTokenMissingException() {
    }

    public AuthorizationTokenMissingException(String message) {
        super(message);
    }

    public AuthorizationTokenMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationTokenMissingException(Throwable cause) {
        super(cause);
    }

    public AuthorizationTokenMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

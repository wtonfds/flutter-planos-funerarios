package br.com.monitoratec.farol.auth.exceptions;

public class AuthorizationTokenUnrecognizedException extends Exception {

    public AuthorizationTokenUnrecognizedException() {
    }

    public AuthorizationTokenUnrecognizedException(String message) {
        super(message);
    }

    public AuthorizationTokenUnrecognizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationTokenUnrecognizedException(Throwable cause) {
        super(cause);
    }

    public AuthorizationTokenUnrecognizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

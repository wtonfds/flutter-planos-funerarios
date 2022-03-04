package br.com.monitoratec.farol.auth.exceptions;

import java.util.ArrayList;
import java.util.List;

public class TimedOutExecutionException extends Exception {
    private List<String> missingPermissions = new ArrayList<>();

    public TimedOutExecutionException() {
    }

    public TimedOutExecutionException(String message) {
        super(message);
    }

    public TimedOutExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimedOutExecutionException(Throwable cause) {
        super(cause);
    }

    public TimedOutExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public List<String> getMissingPermissions() {
        return missingPermissions;
    }

    public void setMissingPermissions(List<String> missingPermissions) {
        this.missingPermissions = missingPermissions;
    }
}

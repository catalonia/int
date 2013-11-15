package com.tastesync.oauth.exception;

public class TasteSyncException extends Exception {
    private static final long serialVersionUID = 6045769976150701803L;

    public TasteSyncException() {
        super();
    }

    public TasteSyncException(String message, Throwable cause) {
        super(message, cause);
    }

    public TasteSyncException(String message) {
        super(message);
    }

    public TasteSyncException(Throwable cause) {
        super(cause);
    }
}

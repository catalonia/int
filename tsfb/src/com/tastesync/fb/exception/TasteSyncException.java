package com.tastesync.fb.exception;

public class TasteSyncException extends Exception {
    private static final long serialVersionUID = -5299507793714753096L;

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

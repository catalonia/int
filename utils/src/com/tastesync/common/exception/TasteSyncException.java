package com.tastesync.common.exception;

public class TasteSyncException extends Exception {
    private static final long serialVersionUID = -1838128543003919170L;

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

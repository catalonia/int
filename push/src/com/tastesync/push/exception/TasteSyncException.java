package com.tastesync.push.exception;

public class TasteSyncException extends Exception {
	private static final long serialVersionUID = 3018656876639813508L;
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

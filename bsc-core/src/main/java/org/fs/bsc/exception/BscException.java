package org.fs.bsc.exception;

public class BscException extends RuntimeException {

    public BscException() {
        super();
    }

    public BscException(String msg) {
        super(msg);
    }

    public BscException(Throwable e) {
        super(e);
    }

    public BscException(String msg, Throwable e) {
        super(msg, e);
    }
}

package com.roy.downloader.core.exception;

public class NormalizeUrlException extends Exception {
    public NormalizeUrlException(String message, Exception e) {
        super(message);
        initCause(e);
    }

    public NormalizeUrlException(String message) {
        super(message);
    }
}

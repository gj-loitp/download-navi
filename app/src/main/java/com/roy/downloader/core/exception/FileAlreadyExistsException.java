package com.roy.downloader.core.exception;

public class FileAlreadyExistsException extends Exception {
    public FileAlreadyExistsException() {
    }

    public FileAlreadyExistsException(String message) {
        super(message);
    }

    public FileAlreadyExistsException(Exception e) {
        super(e.getMessage());
        super.setStackTrace(e.getStackTrace());
    }
}

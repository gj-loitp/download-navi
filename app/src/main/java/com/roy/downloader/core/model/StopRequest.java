package com.roy.downloader.core.model;

/*
 * The class that represents the reason for completing the download.
 */

import static com.roy.downloader.core.model.data.StatusCode.STATUS_UNHANDLED_HTTP_CODE;
import static com.roy.downloader.core.model.data.StatusCode.STATUS_UNHANDLED_REDIRECT;

import androidx.annotation.NonNull;

class StopRequest {
    private String message;
    private final int finalStatus;
    private Throwable t;

    public StopRequest(int finalStatus) {
        this.finalStatus = finalStatus;
    }

    public StopRequest(int finalStatus, String message) {
        this.message = message;
        this.finalStatus = finalStatus;
    }

    public StopRequest(int finalStatus, Throwable t) {
        this(finalStatus, t.getMessage());
        this.t = t;
    }

    public StopRequest(int finalStatus, String message, Throwable t) {
        this(finalStatus, message);
        this.t = t;
    }

    public int getFinalStatus() {
        return finalStatus;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getException() {
        return t;
    }

    public static StopRequest getUnhandledHttpError(int code, String message) {
        final String error = "Unhandled HTTP response: " + code + " " + message;
        if (code >= 400 && code < 600)
            return new StopRequest(code, error);
        else if (code >= 300 && code < 400)
            return new StopRequest(STATUS_UNHANDLED_REDIRECT, error);
        else
            return new StopRequest(STATUS_UNHANDLED_HTTP_CODE, error);
    }

    @NonNull
    @Override
    public String toString() {
        return "StopRequest{" +
                "message='" + message + '\'' +
                ", finalStatus=" + finalStatus +
                ", t=" + t +
                '}';
    }
}

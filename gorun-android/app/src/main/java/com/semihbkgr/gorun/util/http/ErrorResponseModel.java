package com.semihbkgr.gorun.util.http;

public class ErrorResponseModel {

    private long timestamp;
    private int httpStatus;
    private String url;
    private String message;

    public ErrorResponseModel() {
    }

    public ErrorResponseModel(long timestamp, int httpStatus, String url, String message) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.url = url;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

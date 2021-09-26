package com.semihbkgr.gorun.util;

public class RequestException extends Exception{

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException(Throwable cause) {
        super(cause);
    }

}

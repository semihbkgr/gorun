package com.semihbkgr.gorun.server.message;

public class MessageMarshallException extends RuntimeException{

    public MessageMarshallException() {
    }

    public MessageMarshallException(String message) {
        super(message);
    }

    public MessageMarshallException(String message, Throwable cause) {
        super(message, cause);
    }

}

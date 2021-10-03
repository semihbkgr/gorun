package com.semihbkgr.gorun.server.error;

public class CodeExecutionError extends RuntimeException{

    public CodeExecutionError() {
    }

    public CodeExecutionError(String message) {
        super(message);
    }

    public CodeExecutionError(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeExecutionError(Throwable cause) {
        super(cause);
    }

}

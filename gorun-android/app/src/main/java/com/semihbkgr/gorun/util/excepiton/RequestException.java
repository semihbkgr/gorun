package com.semihbkgr.gorun.util.excepiton;

import java.util.Optional;

public class RequestException extends Exception {

    private ErrorResponseModel errorResponseModel;

    public RequestException(Throwable cause) {
        super(cause);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException(ErrorResponseModel errorResponseModel) {
        this.errorResponseModel = errorResponseModel;
    }

    public RequestException(ErrorResponseModel errorResponseModel, Throwable cause) {
        this(cause);
        this.errorResponseModel = errorResponseModel;
    }

    public RequestException(ErrorResponseModel errorResponseModel, String message, Throwable cause) {
        this(message, cause);
        this.errorResponseModel = errorResponseModel;
    }

    public Optional<ErrorResponseModel> errorResponseModelOptional() {
        return Optional.ofNullable(errorResponseModel);
    }

}

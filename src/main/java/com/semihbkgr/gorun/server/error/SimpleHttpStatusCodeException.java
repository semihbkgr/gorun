package com.semihbkgr.gorun.server.error;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class SimpleHttpStatusCodeException extends HttpStatusCodeException {

    public SimpleHttpStatusCodeException(@NonNull HttpStatus statusCode) {
        super(statusCode);
    }

    public SimpleHttpStatusCodeException(int statusCode) {
        this(HttpStatus.resolve(statusCode));
    }

}

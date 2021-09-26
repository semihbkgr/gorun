package com.semihbkgr.gorun.server.api;

import com.semihbkgr.gorun.server.error.ErrorResponseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponseModel> handle(Exception exception, ServerWebExchange exchange) {
        if (exception instanceof HttpStatusCodeException) {
            HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) exception;
            return Mono.just(
                    ErrorResponseModel.builder()
                            .timestamp(System.currentTimeMillis())
                            .httpStatus(httpStatusCodeException.getRawStatusCode())
                            .url(exchange.getRequest().getURI().toString())
                            .message(exception.getMessage())
                            .build()
            );
        } else
            return Mono.just(
                    ErrorResponseModel.builder()
                            .timestamp(System.currentTimeMillis())
                            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .url(exchange.getRequest().getURI().toString())
                            .message(exception.getMessage())
                            .build()
            );
    }

}

package com.semihbkgr.gorun.server.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseModel {

    private long timestamp;
    private int httpStatus;
    private String url;
    private String message;

}

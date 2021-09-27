package com.semihbkgr.gorun.util.http;

import java.util.function.Function;

public interface ResponseCallback <T>{

    void onResponse(T data);

    void onFailure(Exception e);

    default <N> ResponseCallback<N> convertResponseType(Function<N,T> converter){
        return new ResponseCallback<N>() {
            @Override
            public void onResponse(N data) {
                ResponseCallback.this.onResponse(converter.apply(data));
            }

            @Override
            public void onFailure(Exception e) {
                ResponseCallback.this.onFailure(e);
            }
        };
    }

}

package com.semihbkgr.gorun.util;

public interface ResponseCallback <T>{

    void onResponse(T data);

    void onFailure(Throwable t);

}

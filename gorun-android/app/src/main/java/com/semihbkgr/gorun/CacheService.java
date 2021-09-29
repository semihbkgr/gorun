package com.semihbkgr.gorun;

public interface CacheService<ID,T> {

    boolean exists(ID id);

    T get(ID id);



}

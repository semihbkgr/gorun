package com.semihbkgr.gorun.cache;

import java.util.Optional;

public interface CacheService<ID,T> {

    boolean isAvailable(ID id);

    Optional<T> get(ID id);

    T get(ID id,T def);

    boolean invalidate(ID id);

    void invalidateAll();

}

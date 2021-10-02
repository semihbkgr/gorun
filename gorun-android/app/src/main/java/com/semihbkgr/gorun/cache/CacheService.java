package com.semihbkgr.gorun.cache;

import java.util.Optional;

public interface CacheService<I, R> {

    boolean isAvailable(I id);

    Optional<R> get(I id);

    R get(I id, R def);

    void cache(I id,R record);

    boolean invalidate(I id);

    void invalidateAllExpiredCaches();

}

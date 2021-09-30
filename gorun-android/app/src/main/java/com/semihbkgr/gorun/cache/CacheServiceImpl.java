package com.semihbkgr.gorun.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CacheServiceImpl<I, R> implements CacheService<I, R> {

    private final Map<I, R> recordMap;

    public CacheServiceImpl() {
        this.recordMap=new HashMap<>();
    }

    @Override
    public boolean isAvailable(I id) {
        return recordMap.containsKey(id);
    }

    @Override
    public Optional<R> get(I id) {
        return Optional.ofNullable(recordMap.get(id));
    }

    @Override
    public R get(I id, R def) {
        return isAvailable(id)?recordMap.get(id):def;
    }

    @Override
    public boolean invalidate(I id) {
        return recordMap.remove(id)!=null;
    }

    @Override
    public void invalidateAll() {
        recordMap.clear();
    }

}

package com.semihbkgr.gorun.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class TimedCacheService<I, R> implements CacheService<I, R> {

    private final HashMap<I, TimedCacheRecordHolder<R>> timedRecordMap;
    private final long expirationTime;

    public TimedCacheService(long expirationTime) {
        this.expirationTime = expirationTime;
        this.timedRecordMap = new HashMap<>();
    }

    @Override
    public boolean isAvailable(I id) {
        TimedCacheRecordHolder<R> recordHolder = timedRecordMap.get(id);
        return recordHolder != null && !checkExpiration(recordHolder);
    }

    @Override
    public Optional<R> get(I id) {
        return isAvailable(id) ?
                Optional.of(timedRecordMap.get(id).record) :
                Optional.empty();
    }

    @Override
    public R get(I id, R def) {
        return isAvailable(id) ?
                timedRecordMap.get(id).record :
                def;
    }

    @Override
    public boolean invalidate(I id) {
        return timedRecordMap.remove(id)!=null;
    }

    @Override
    public void invalidateAll() {
        timedRecordMap.clear();
    }

    public void checkExpiration(){
        final long currentTime=System.currentTimeMillis();
        timedRecordMap.entrySet().removeIf(entry ->
                (currentTime - entry.getValue().time) > expirationTime);
    }

    private boolean checkExpiration(TimedCacheRecordHolder<R> recordHolder) {
        return (System.currentTimeMillis() - recordHolder.time) > expirationTime;
    }

}

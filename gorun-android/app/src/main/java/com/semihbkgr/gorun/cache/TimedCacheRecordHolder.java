package com.semihbkgr.gorun.cache;

class TimedCacheRecordHolder<T> {

    final long time;
    final T record;

    public TimedCacheRecordHolder(T record) {
        this.record = record;
        this.time = System.currentTimeMillis();
    }

}

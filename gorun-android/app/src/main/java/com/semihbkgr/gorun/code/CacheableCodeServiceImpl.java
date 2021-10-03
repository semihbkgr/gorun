package com.semihbkgr.gorun.code;

import androidx.annotation.NonNull;
import com.semihbkgr.gorun.cache.TimedCacheService;
import org.jetbrains.annotations.NotNull;

public class CacheableCodeServiceImpl extends CodeServiceImpl {

    private final TimedCacheService<Integer,Code> codeCacheService;

    public CacheableCodeServiceImpl(@NonNull @NotNull CodeRepository codeRepository) {
        super(codeRepository);
        this.codeCacheService=new TimedCacheService<>();
    }

}

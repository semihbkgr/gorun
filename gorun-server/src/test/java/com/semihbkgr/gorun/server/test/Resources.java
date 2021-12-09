package com.semihbkgr.gorun.server.test;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Resources {

    static final Map<String, String> nameContentResourceMap = new HashMap<>();

    public static String getResourceAsString(@NonNull String filename) {
        if (nameContentResourceMap.containsKey(filename))
            return nameContentResourceMap.get(filename);
        throw new IllegalArgumentException("Resource does not exists, filename: " + filename);
    }

}

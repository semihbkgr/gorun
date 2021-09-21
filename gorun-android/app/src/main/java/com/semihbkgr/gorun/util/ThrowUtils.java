package com.semihbkgr.gorun.util;

public class ThrowUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Exception, P> P sneakyThrow(Exception exception) throws T {
        throw (T) exception;
    }

}

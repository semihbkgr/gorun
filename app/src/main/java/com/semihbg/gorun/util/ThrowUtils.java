package com.semihbg.gorun.util;

public class ThrowUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Exception> void sneakyThrow(Exception exception) throws T{
        throw (T) exception;
    }

}

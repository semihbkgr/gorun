package com.semihbg.gorun.run;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CodeRunUtils {

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

    public static InputStream error(){
        return new ByteArrayInputStream("ERROR".getBytes());
    }


}

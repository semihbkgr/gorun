package com.semihbg.gorun.run;

import java.io.InputStream;
import java.util.function.Consumer;

public interface CodeRunClient {

    CodeRunClient DEFAULT=new DefaultCodeRunClient();

    String SERVER_ROOT_URI="http://192.168.1.12:8080";
    String RUN_API_URI=SERVER_ROOT_URI+"/run";

    void run(String code, Consumer<InputStream> inputStreamConsumer);

}

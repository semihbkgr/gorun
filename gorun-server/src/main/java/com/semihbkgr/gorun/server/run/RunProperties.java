package com.semihbkgr.gorun.server.run;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
public class RunProperties {

    private String url;
    private Duration timeout;
    private Duration timeoutCheck;

}

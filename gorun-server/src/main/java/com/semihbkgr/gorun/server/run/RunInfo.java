package com.semihbkgr.gorun.server.run;

import lombok.Data;

@Data
public class RunInfo {

    private final String code;
    private long startTimeMS;
    private long finishTimeMS;
    private RunState lastState;

}

package com.semihbkgr.gorun.server.run;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RunInfo {

    private final String code;
    private long startTimeMS;
    private long finishTimeMS;
    private RunStatus lastState;

}

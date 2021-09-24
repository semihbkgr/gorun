package com.semihbkgr.gorun.server.run;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class RunInfo {

    private final String fileName;
    private long startTimeMS;
    private long finishTimeMS;

}

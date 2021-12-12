package com.semihbkgr.gorun.server.code.file;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SequentialFilenameGenerator implements FilenameGenerator {

    private final AtomicInteger sequence;

    public SequentialFilenameGenerator() {
        this.sequence = new AtomicInteger(0);
    }

    @Override
    public String generate(@NonNull String extension) {
        return String.valueOf(sequence.getAndIncrement())
                .concat(".")
                .concat(extension);
    }

}

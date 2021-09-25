package com.semihbkgr.gorun.server.component;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SequentialFileNameGenerator implements FileNameGenerator{

    private final AtomicInteger sequence;

    public SequentialFileNameGenerator() {
        UUID.randomUUID()
        this.sequence = new AtomicInteger(0);
    }

    @Override
    public String generate(String extension) {
        return String.valueOf(sequence.getAndIncrement())
                .concat(".")
                .concat(extension);
    }

}

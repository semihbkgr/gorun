package com.semihbkgr.gorun.server.code.file;

import com.semihbkgr.gorun.server.file.SequentialFilenameGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SequentialFilenameGeneratorTest {

    SequentialFilenameGenerator nameGenerator = new SequentialFilenameGenerator();

    @Test
    @DisplayName("GenerateByNullExtension")
    void generateBYNullExtension() {
        assertThrows(NullPointerException.class, () -> nameGenerator.generate(null));
    }

    @Test
    @DisplayName("GenerateMultipleUniqueName")
    void generateMultipleUniqueName() {
        final var set = new HashSet<>();
        IntStream.range(0, 100).forEach(i -> {
            var filename = nameGenerator.generate("txt");
            if (set.contains(filename))
                fail("Filename : " + filename + " has already been generated");
            set.add(filename);
        });
    }

    @Test
    @DisplayName("GenerateMultipleSequentialName")
    void generateMultipleSequentialName() {
        assertTrue(
                IntStream.rangeClosed(0, 100)
                        .allMatch(i ->
                                nameGenerator.generate(".txt").startsWith(String.valueOf(i))),
                "Generated filenames are not sequential");
    }

}
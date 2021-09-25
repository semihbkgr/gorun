package com.semihbkgr.gorun.server.service;

import org.junit.jupiter.api.*;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileServiceImplTest {

    static final String ROOT_PATH = "test-files";

    final FileServiceImpl fileService = new FileServiceImpl(ROOT_PATH);

    @BeforeEach
    void createRootDir() throws IOException {
        fileService.createRootDirIfNotExists();
    }

    @AfterEach
    void deleteRootDir() throws IOException {
        fileService.clearAndDeleteRootDir();
    }

    @Test
    void createFile() {
        final var fileName = "test.txt";
        final var fileContent = "FileServiceImplTest#createFile()";
        var mono = fileService.createFile(fileName, fileContent).log();
        StepVerifier.create(mono)
                .expectNext(Path.of(ROOT_PATH).resolve(fileName).toString())
                .verifyComplete();
    }

    @Test
    void deleteFile() throws IOException {
        final var fileName = "test.txt";
        final var fileContent = "FileServiceImplTest#createFile()";
        var filePath = Files.createFile(Path.of(ROOT_PATH).resolve(fileName));
        Files.write(filePath, fileContent.getBytes());
        var mono = fileService.deleteFile(fileName).log();
        StepVerifier.create(mono)
                .expectNext(filePath.toString())
                .verifyComplete();
    }

}
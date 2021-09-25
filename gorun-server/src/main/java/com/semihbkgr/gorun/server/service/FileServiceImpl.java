package com.semihbkgr.gorun.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final Path rootPath;

    public FileServiceImpl(@Value("${file.root-path:files}") String rootFolderPath) {
        this.rootPath = Path.of(rootFolderPath);
    }

    @PostConstruct
    void createRootDirIfNotExists() throws IOException {
        if (!Files.exists(rootPath)) {
            log.info("Root dir does not exists, Path : {}", rootPath);
            Files.createDirectories(rootPath);
            log.info("Root dir has been created successfully, Path : {}", rootPath);
        } else
            log.info("Root dir already exists, Path : {}", rootPath);
    }

    @PreDestroy
    void clearAndDeleteRootDir() throws IOException {
        FileSystemUtils.deleteRecursively(rootPath);
    }

    @Override
    public Mono<String> createFile(String fileName, String content) {
        return Mono.create(sink -> {
            try {
                var filePath = Files.createFile(rootPath.resolve(fileName));
                Files.write(filePath, content.getBytes());
                sink.success(filePath.toString());
            } catch (IOException e) {
                sink.error(e);
            }
        });
    }

    @Override
    public Mono<String> deleteFile(String fileName) {
        return Mono.create(sink -> {
            try {
                var filePath = rootPath.resolve(fileName);
                Files.delete(filePath);
                sink.success(filePath.toString());
            } catch (IOException e) {
                sink.error(e);
            }
        });
    }

    @Override
    public Path getRootDirPath() {
        return this.rootPath;
    }

}

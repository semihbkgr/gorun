package com.semihbkgr.gorun.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
        clearAllFilesAndDeleteDir(rootPath);
    }

    private void clearAllFilesAndDeleteDir(Path dir) throws IOException {
        if (Files.isDirectory(dir)) {
            Files.walk(dir).parallel().forEach(subPath -> {
                if (subPath == dir) return;
                if (Files.isDirectory(subPath)) {
                    try {
                        clearAllFilesAndDeleteDir(subPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Files.delete(subPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
            Files.delete(dir);
        }
    }

    @Override
    public Mono<String> createFile(String fileName, String content) {
        return Mono.create(sink -> {
            try {
                var filePath = Files.createFile(rootPath.resolve(fileName));
                Files.write(filePath, content.getBytes());
                sink.success(fileName);
            } catch (IOException e) {
                sink.error(e);
            }
        });
    }

    @Override
    public Mono<String> deleteFile(String fileName) {
        return Mono.create(sink -> {
            try {
                Files.delete(rootPath.resolve(fileName));
                sink.success(fileName);
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

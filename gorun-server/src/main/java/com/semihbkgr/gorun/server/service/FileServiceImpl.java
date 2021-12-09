package com.semihbkgr.gorun.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final Path rootPath;

    public FileServiceImpl(@Value("${file.root-path:files}") String rootFolderPath) {
        this.rootPath = Path.of(rootFolderPath);
    }

    @PostConstruct
    void createRootDir() throws IOException {
        log.info("Root dir path: {}", rootPath);
        if (Files.exists(rootPath)) {
            log.info("Root dir already exists");
            clearRootDir();
            log.info("Root dir has been cleared successfully");
        } else {
            log.info("Root dir doesn't exist");
            Files.createDirectories(rootPath);
            log.info("Root dir has been created successfully");
        }
    }

    @PreDestroy
    void deleteRootDir() throws IOException {
        if (Files.exists(rootPath)) {
            clearRootDir();
            log.info("Root dir has been cleared successfully");
            Files.delete(rootPath);
            log.info("Root dir has been deleted successfully");
        }
    }

    private void clearRootDir() throws IOException {
        Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (!dir.equals(rootPath)) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
                return FileVisitResult.TERMINATE;
            }
        });
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
    public Mono<String> deleteFile(String filepath) {
        return Mono.create(sink -> {
            try {
                var filePath = Path.of(filepath);
                if (!filePath.isAbsolute())
                    filePath = rootPath.resolve(filepath);
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

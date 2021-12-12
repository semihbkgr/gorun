package com.semihbkgr.gorun.server.file;

import reactor.core.publisher.Mono;

import java.nio.file.Path;

public interface FileService {

    Mono<String> createFile(String filename, String content);

    Mono<String> deleteFile(String filename);

    Path getRootDirPath();

}

package com.semihbkgr.gorun.server.service;

import reactor.core.publisher.Mono;

import java.nio.file.Path;

public interface FileService {

    Mono<Void> createFile(String fileName, String content);

    Mono<Void> deleteFile(String fileName);

    Path getRootDirPath();

}

package com.semihbkgr.gorun.server.service;

import reactor.core.publisher.Mono;

import java.nio.file.Path;

public interface FileService {

    Mono<String> createFile(String fileName, String content);

    Mono<String> deleteFile(String fileName);

    Path getRootDirPath();

}

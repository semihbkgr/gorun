package com.semihbg.gorun.server.service;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {

    String createFile(String fileName, String content);

    void deleteFile(String fileName);

    Path getRootPath();

}

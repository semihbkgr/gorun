package com.semihbg.gorun.server.service;

import java.io.IOException;

public interface FileService {

    String createFile(String fileName, String content);

    void deleteFile(String fileName);

}

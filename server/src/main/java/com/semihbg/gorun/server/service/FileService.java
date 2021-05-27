package com.semihbg.gorun.server.service;

public interface FileService {

    boolean createFile(String fileName,String content);

    boolean deleteFile(String fileName);

}

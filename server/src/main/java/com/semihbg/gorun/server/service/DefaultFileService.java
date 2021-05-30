package com.semihbg.gorun.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class DefaultFileService implements FileService{

    private final Path rootPath;

    public DefaultFileService(@Value("${file-service.path:${user.dir}}") String rootFolderPath) throws IOException {
        this.rootPath=Path.of(rootFolderPath);
        if(!Files.exists(rootPath)){
            log.info("RootPath folder is not exists, Path : {}",rootFolderPath);
            Files.createDirectories(rootPath);
            log.info("RootPath folder is created, Path : {}",rootFolderPath);
        }else
            log.info("RootPath is already exists, Path : {}",rootFolderPath);

    }

    @Override
    public String createFile(String fileName, String content){
        try{
            Path filePath=rootPath.resolve(fileName);
            Files.createFile(filePath);
            Files.write(filePath,content.getBytes());
            return filePath.toString();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("FileCreateException");
        }
    }

    @Override
    public void deleteFile(String fileName){
        try{
            Path filePath=rootPath.resolve(fileName);
            Files.delete(filePath);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("FileDeleteException");
        }
    }

}

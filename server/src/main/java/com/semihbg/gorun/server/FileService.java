package com.semihbg.gorun.server;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FileService {

    private final Path path;

    private final ThreadLocal<Integer> fileNameTL;
    private final AtomicInteger fileNameInteger;

    @SneakyThrows
    public FileService(@Value("${file-service.path}") String pathString) {
        this.path = Path.of(pathString);
        if(!Files.exists(path))
            Files.createDirectories(path);
        this.fileNameTL=new ThreadLocal<>();
        fileNameInteger=new AtomicInteger(0);
    }

    @SneakyThrows
    public void createFile(String content){
        int fileNameIntegerValue=fileNameInteger.incrementAndGet();
        fileNameTL.set(fileNameIntegerValue);
        Path filePath=path.resolve(String.valueOf(fileNameIntegerValue).concat(".go"));
        Files.createFile(filePath);
        Files.write(filePath,content.getBytes());
    }

    @SneakyThrows
    public void deleteFile(){
        int fileNameIntegerValue=fileNameTL.get();
        fileNameTL.remove();
        Path filePath=path.resolve(String.valueOf(fileNameIntegerValue).concat(".go"));
        Files.delete(filePath);
    }

    public String getFileName(){
        return String.valueOf(fileNameTL.get()).concat(".go");
    }

    public String getFileFullPathString(){
        return path.resolve(String.valueOf(fileNameTL.get()).concat(".go")).toString();
    }

}

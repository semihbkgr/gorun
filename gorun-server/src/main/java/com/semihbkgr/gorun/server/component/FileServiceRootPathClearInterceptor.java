package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.service.FileService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileServiceRootPathClearInterceptor implements CommandLineRunner {

    private final FileService fileService;

    @Override
    public void run(String... args) throws IOException {
        AtomicInteger counter=new AtomicInteger(0);
        AtomicBoolean isContainsDirectory=new AtomicBoolean(false);
        AtomicBoolean ignoreRootDirectory=new AtomicBoolean(true);
        Files.walk(fileService.getRootPath()).forEach(path->{
            if(ignoreRootDirectory.get()){
                ignoreRootDirectory.set(false);
                return;
            }
            if(Files.isDirectory(path)) {
                isContainsDirectory.set(true);
                log.warn("FileService's root path contains a directory, DirName : {}", path.getFileName().getName(0));
            }else{
                deleteFile(path);
                counter.incrementAndGet();
            }
        });
        if(counter.get()>0){
            log.warn("FileService's root path was contains some files, {} files deleted",counter.get());
        }else if(!isContainsDirectory.get()){
            log.info("FileService's root path is clear");
        }
    }

    @SneakyThrows
    private void deleteFile(@NonNull Path path){
        Files.delete(path);
    }

}

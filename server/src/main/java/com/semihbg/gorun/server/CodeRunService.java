package com.semihbg.gorun.server;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class CodeRunService {


    private final FileService fileService;

    @SneakyThrows
    public Flux<String> run(String code) {
        return Flux.create(stringFluxSink -> {
            fileService.createFile(code);
            String fileName = fileService.getFileName();
            String fileFullPathString = fileService.getFileFullPathString();
            try {
                Process process = Runtime.getRuntime().exec("go run " + fileFullPathString);
                InputStream inputStream = process.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    stringFluxSink.next(line.concat(System.lineSeparator()));
            } catch (IOException e) {
                e.printStackTrace();
                stringFluxSink.error(e);
            } finally {
                fileService.deleteFile();
            }
            stringFluxSink.complete();
        });

    }


}

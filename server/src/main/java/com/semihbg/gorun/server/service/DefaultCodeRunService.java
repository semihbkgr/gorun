package com.semihbg.gorun.server.service;

import com.semihbg.gorun.server.component.FileNameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class DefaultCodeRunService implements CodeRunService {

    private final FileNameGenerator fileNameGenerator;
    private final FileService fileService;

    @Override
    public Flux<String> run(String code) {
        return Flux.create(stringFluxSink -> {
            String fileName=fileNameGenerator.generate(".go");
            fileService.createFile(fileName,code);
            try {
                Process process = Runtime.getRuntime().exec("go run " + fileName);
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
                fileService.deleteFile(fileName);
            }
            stringFluxSink.complete();
        });
    }

}

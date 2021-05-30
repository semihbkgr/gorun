package com.semihbg.gorun.server.service;

import com.semihbg.gorun.server.component.FileNameGenerator;
import com.semihbg.gorun.server.message.Command;
import com.semihbg.gorun.server.message.Message;
import com.semihbg.gorun.server.socket.CodeRunContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

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
    public Flux<Message> run(CodeRunContext codeRunContext) {
        Flux<Message> messageFlux= Flux.create(stringFluxSink -> {
            String fileName = fileNameGenerator.generate("go");
            String filePath = fileService.createFile(fileName, codeRunContext.getCode());
            codeRunContext.start();
            stringFluxSink.next(Message.of(Command.START));
            try {
                Process process = Runtime.getRuntime().exec(new String[]{"go", "run", filePath});
                try (InputStream inputStream = process.getInputStream();
                     InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null)
                        stringFluxSink.next(Message.of(Command.OUTPUT, line));
                }
            } catch (IOException e) {
                e.printStackTrace();
                stringFluxSink.next(Message.of(Command.SYSTEM, "IOException"));
            } finally {
                fileService.deleteFile(fileName);
            }
            codeRunContext.end();
            stringFluxSink.next(Message.of(Command.END));
            stringFluxSink.complete();
        });
        return messageFlux.subscribeOn(Schedulers.boundedElastic());
    }

}

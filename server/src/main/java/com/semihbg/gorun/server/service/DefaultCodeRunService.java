package com.semihbg.gorun.server.service;

import com.semihbg.gorun.server.component.CodeRunHandler;
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
    private final CodeRunHandler codeRunHandler;
    private final FileService fileService;

    @Override
    public Flux<Message> run(CodeRunContext codeRunContext) {
        Flux<Message> messageFlux= Flux.create(stringFluxSink -> {
            String fileName = fileNameGenerator.generate("go");
            String filePath = fileService.createFile(fileName, codeRunContext.getCode());
            try {
                Process process = new ProcessBuilder()
                        .command("go", "run", filePath)
                        .redirectErrorStream(true)
                        .start();
                codeRunContext.start(process);
                codeRunHandler.registerRunning(Thread.currentThread(),codeRunContext);
                stringFluxSink.next(Message.of(Command.START));
                try (InputStream inputStream = process.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    char[] oneLengthCharBuffer=new char[1];
                    while (bufferedReader.read(oneLengthCharBuffer) != -1)
                        stringFluxSink.next(Message.of(Command.OUTPUT, String.valueOf(oneLengthCharBuffer)));
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

    @Override
    public Flux<Void> execute(Execution execution) {
        Schedulers.boundedElastic().schedule(execution.toRunnable());
        return Flux.empty();
    }

}

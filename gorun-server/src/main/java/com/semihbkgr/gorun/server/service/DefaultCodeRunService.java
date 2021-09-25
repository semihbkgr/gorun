package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.component.CodeRunHandler;
import com.semihbkgr.gorun.server.component.FileNameGenerator;
import com.semihbkgr.gorun.server.message.Command;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.run.DefaultRunContextt;
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
    public Flux<Message> run(DefaultRunContextt defaultRunContextt) {
        Flux<Message> messageFlux= Flux.create(stringFluxSink -> {
            String fileName = fileNameGenerator.generate("go");
            String filePath = fileService.createFile(fileName, defaultRunContextt.getCode());
            try {
                Process process = new ProcessBuilder()
                        .command("go", "run", filePath)
                        .redirectErrorStream(true)
                        .start();
                defaultRunContextt.start(process);
                codeRunHandler.registerRunning(Thread.currentThread(), defaultRunContextt);
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
            defaultRunContextt.end();
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

package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.component.FileNameGenerator;
import com.semihbkgr.gorun.server.component.ProcessTimeoutHandler;
import com.semihbkgr.gorun.server.component.ProcessTimeoutHandlerImpl;
import com.semihbkgr.gorun.server.component.SequentialFileNameGenerator;
import com.semihbkgr.gorun.server.message.Action;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.socket.RunWebSocketSession;
import com.semihbkgr.gorun.server.test.ResourceExtension;
import com.semihbkgr.gorun.server.test.Resources;
import com.semihbkgr.gorun.server.test.ResourcesDir;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;

@ExtendWith(ResourceExtension.class)
@ResourcesDir(path = "./src/test/resources")
class MessageProcessingServiceImplTest {

    static final String ROOT_DIR = "test-files";

    MessageProcessingServiceImpl messageProcessService;
    RunWebSocketSession session;
    FileServiceImpl fileService;
    FileNameGenerator fileNameGenerator;
    ProcessTimeoutHandler processTimeoutHandler;

    @BeforeEach
    void initializeRequiredObjects() throws IOException {
        this.session = new RunWebSocketSession();
        this.fileService = new FileServiceImpl(ROOT_DIR);
        fileService.createRootDirIfNotExists();
        this.fileNameGenerator = new SequentialFileNameGenerator();
        this.processTimeoutHandler=new ProcessTimeoutHandlerImpl(30_000);
        this.messageProcessService = new MessageProcessingServiceImpl(fileService, fileNameGenerator,processTimeoutHandler);
    }

    @AfterEach
    void deleteFileServiceRootDir() throws IOException {
        fileService.clearAndDeleteRootDir();
    }

    // Test methods below test only one message at a time.

    @Test
    @DisplayName("CommandRunWhenOnGoingProcessDoesNotExists")
    void commandRunWhenOnGoingProcessDoesNotExists() {
        var messageBody = Resources.getResourceAsString("hello.go");
        var session = new RunWebSocketSession();
        var message = Message.of(Action.RUN, messageBody);
        var messageFlux = messageProcessService.process(session, message).log();
        StepVerifier.create(messageFlux)
                .expectNext(Message.of(Action.OUTPUT, "Hello"))
                .verifyComplete();
    }

    @Test
    @DisplayName("CommandInputWhenOnGoingProcessDoesNotExists")
    void commandInputWhenOnGoingProcessDoesNotExists() {
        var message = Message.of(Action.INPUT, "inputData");
        var messageFlux = messageProcessService.process(session, message).log();
        StepVerifier.create(messageFlux)
                .expectNextMatches(responseMessage -> responseMessage.action == Action.WARN)
                .verifyComplete();
    }

    @Test
    @DisplayName("CommandInterruptWhenOnGoingProcessDoesNotExists")
    void commandInterruptWhenOnGoingProcessDoesNotExists() {
        var message = Message.of(Action.INTERRUPT);
        var messageFlux = messageProcessService.process(session, message).log();
        StepVerifier.create(messageFlux)
                .expectNextMatches(responseMessage -> responseMessage.action == Action.WARN)
                .verifyComplete();
    }


    // Test methods below test a sequence of messages at a time.

    @Test
    @DisplayName("CommandRunAndInput")
    void commandRunAndInput() throws InterruptedException {
        var runMessage = Message.of(Action.RUN, Resources.getResourceAsString("input.go"));
        var inputData = "inputData";
        var line1 = "Input = ";
        var line2 = "Input: " + inputData;
        var inputMessage = Message.of(Action.INPUT, inputData);
        var messageFlux = messageProcessService.process(session, runMessage).log()
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(responseMessage -> {
                    if (responseMessage.body.equals(line1)) {
                        StepVerifier.create(messageProcessService.process(session, inputMessage).log())
                                .expectNext(Message.of(Action.INFO, inputData))
                                .verifyComplete();
                    }
                });
        StepVerifier.create(messageFlux)
                .expectNext(Message.of(Action.OUTPUT, line1))
                .expectNext(Message.of(Action.OUTPUT, line2))
                .verifyComplete();
    }

    @Test
    @DisplayName("CommandRunWhenOnGoingProcessExists")
    void commandRunWhenOnGoingProcessExists() {
        var message = Message.of(Action.RUN, Resources.getResourceAsString("input.go"));
        var line1 = "Input = ";
        var messageFlux = messageProcessService.process(session, message).log()
                .subscribeOn(Schedulers.boundedElastic())
                .take(Duration.ofMillis(5_000))
                .doOnNext(responseMessage -> {
                    if (responseMessage.body.equals(line1)) {
                        StepVerifier.create(messageProcessService.process(session, message).log())
                                .expectNextMatches(errorMessage -> errorMessage.action == Action.WARN)
                                .verifyComplete();
                    }
                });
        StepVerifier.create(messageFlux)
                .expectNext(Message.of(Action.OUTPUT, line1))
                .thenAwait(Duration.ofMillis(5_000))
                .thenCancel()
                .verify();
    }


}
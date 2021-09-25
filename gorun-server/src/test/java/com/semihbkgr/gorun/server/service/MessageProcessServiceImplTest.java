package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.component.FileNameGenerator;
import com.semihbkgr.gorun.server.component.SequentialFileNameGenerator;
import com.semihbkgr.gorun.server.message.Command;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.socket.RunWebSocketSession;
import com.semihbkgr.gorun.server.test.ResourcesDir;
import com.semihbkgr.gorun.server.test.ResourceExtension;
import com.semihbkgr.gorun.server.test.Resources;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.test.StepVerifier;

import java.io.IOException;

@ExtendWith(ResourceExtension.class)
@ResourcesDir(path = "./src/test/resources")
class MessageProcessServiceImplTest {

    static final String ROOT_DIR = "test-files";

    MessageProcessServiceImpl messageProcessService;
    RunWebSocketSession session;
    FileServiceImpl fileService;
    FileNameGenerator fileNameGenerator;

    @BeforeEach
    void initializeRequiredObjects() throws IOException {
        this.session = new RunWebSocketSession();
        this.fileService = new FileServiceImpl(ROOT_DIR);
        fileService.createRootDirIfNotExists();
        this.fileNameGenerator = new SequentialFileNameGenerator();
        this.messageProcessService = new MessageProcessServiceImpl(fileService, fileNameGenerator);
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
        var message = Message.of(Command.RUN, messageBody);
        var messageFlux = messageProcessService.process(session, message).log();
        StepVerifier.create(messageFlux)
                .expectNext(Message.of(Command.OUTPUT, "Hello"))
                .verifyComplete();
    }

    @Test
    @DisplayName("CommandInputWhenOnGoingProcessDoesNotExists")
    void commandInputWhenOnGoingProcessDoesNotExists() {
        var message = Message.of(Command.INPUT, "inputData");
        var messageFlux = messageProcessService.process(session, message).log();
        StepVerifier.create(messageFlux)
                .expectNextMatches(responseMessage -> responseMessage.command == Command.ERROR)
                .verifyComplete();
    }

    @Test
    @DisplayName("CommandInterruptWhenOnGoingProcessDoesNotExists")
    void commandInterruptWhenOnGoingProcessDoesNotExists() {
        var message = Message.of(Command.INTERRUPT);
        var messageFlux = messageProcessService.process(session, message).log();
        StepVerifier.create(messageFlux)
                .expectNextMatches(responseMessage -> responseMessage.command == Command.ERROR)
                .verifyComplete();
    }



    // Test methods below test a sequence of messages at a time.

    @Test
    @DisplayName("CommandRunWhenOnGoingProcessExists")
    void commandRunWhenOnGoingProcessExists(){

    }

}
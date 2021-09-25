package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.component.FileNameGenerator;
import com.semihbkgr.gorun.server.component.SequentialFileNameGenerator;
import com.semihbkgr.gorun.server.message.Command;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.socket.RunWebSocketSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MessageProcessServiceImplTest {

    static final String ROOT_DIR="test-files";

    MessageProcessServiceImpl messageProcessService;
    RunWebSocketSession session;
    FileServiceImpl fileService;
    FileNameGenerator fileNameGenerator;

    @BeforeEach
    void initializeRequiredObjects() throws IOException {
        this.session=new RunWebSocketSession();
        this.fileService=new FileServiceImpl(ROOT_DIR);
        fileService.createRootDirIfNotExists();
        this.fileNameGenerator=new SequentialFileNameGenerator();
        this.messageProcessService=new MessageProcessServiceImpl(fileService,fileNameGenerator);
    }

    @AfterEach
    void deleteFileServiceRootDir() throws IOException {
        fileService.clearAndDeleteRootDir();
    }

    @Test
    void commandRun(){
        var messageBody="package main\n" +
                "\n" +
                "func main(){\n" +
                "\tprint(\"HelloWorld\")\n" +
                "}\n";
        var session=new RunWebSocketSession();
        var message= Message.of(Command.RUN,messageBody);
        var messageFlux=messageProcessService.process(session,message);
        StepVerifier.create(messageFlux)
                .expectNext(Message.of(Command.OUTPUT,"HelloWorld"))
                .verifyComplete();
    }

}
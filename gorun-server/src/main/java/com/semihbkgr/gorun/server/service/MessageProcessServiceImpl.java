package com.semihbkgr.gorun.server.service;

import com.semihbkgr.gorun.server.component.FileNameGenerator;
import com.semihbkgr.gorun.server.message.Message;
import com.semihbkgr.gorun.server.run.RunStatus;
import com.semihbkgr.gorun.server.socket.RunWebSocketSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class MessageProcessServiceImpl implements MessageProcessService {

    private final FileService fileService;
    private final FileNameGenerator fileNameGenerator;

    @Override
    public Flux<Message> process(RunWebSocketSession session, Message message) {
        switch (message.command){
            case RUN:
                return processRunCommand(session,message);

        }
    }

    private Flux<Message> processRunCommand(RunWebSocketSession session, Message message){
        if(session.runContext!=null && session.runContext.getStatus()!=RunStatus.EXECUTING){
            return fileService.createFile(fileNameGenerator.generate("go"),)
        }else
            return Flux.just(Message.of(Command.ERROR, "This session already has an on going process"));
    }


}

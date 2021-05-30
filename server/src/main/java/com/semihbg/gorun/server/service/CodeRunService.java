package com.semihbg.gorun.server.service;

import com.semihbg.gorun.server.message.Message;
import com.semihbg.gorun.server.socket.CodeRunContext;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicBoolean;

public interface CodeRunService {

    Flux<Message> run(CodeRunContext codeRunContext);

}

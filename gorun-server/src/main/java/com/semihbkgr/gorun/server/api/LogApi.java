package com.semihbkgr.gorun.server.api;

import com.semihbkgr.gorun.server.run.DefaultRunContext;
import com.semihbkgr.gorun.server.service.CodeRunLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogApi {

    private final CodeRunLogService codeRunLogService;

    @GetMapping
    public Flux<DefaultRunContext> logs(){
        return Flux.fromIterable(codeRunLogService.getLogs());
    }

}

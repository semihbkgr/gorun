package com.semihbg.gorun.server.service;

import reactor.core.publisher.Flux;

public interface CodeRunService {

    Flux<String> run(String code);

}

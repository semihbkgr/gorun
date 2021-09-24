package com.semihbkgr.gorun.server.command;

import java.util.Objects;

public class Message {

    public final Command command;
    public final String body;

    private Message(Command command, String body) {
        this.command = command;
        this.body = body;
    }

    public static Message of(Command command, String body) {
        return new Message(Objects.requireNonNull(command), Objects.requireNonNull(body));
    }

    public static Message of(Command command) {
        return new Message(Objects.requireNonNull(command), null);
    }


}

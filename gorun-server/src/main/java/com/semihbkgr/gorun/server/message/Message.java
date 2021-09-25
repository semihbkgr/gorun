package com.semihbkgr.gorun.server.message;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Message {

    public final Command command;
    public final String body;

    private Message(Command command, String body) {
        this.command = command;
        this.body = body;
    }

    public static Message of(@NonNull Command command,@NonNull String body) {
        return new Message(command,body);
    }

    public static Message of(@NonNull Command command) {
        return new Message(command, null);
    }

}

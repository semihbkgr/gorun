package com.semihbkgr.gorun.server.message;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Message {

    /*
     * Message format
     * [action:body]
     * [action]
     */

    public final Action action;
    public final String body;

    private Message(Action action, String body) {
        this.action = action;
        this.body = body;
    }

    public static Message of(@NonNull Action action, @NonNull String body) {
        return new Message(action, body);
    }

    public static Message of(@NonNull Action action) {
        return new Message(action, null);
    }

}

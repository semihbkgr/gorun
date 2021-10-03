package com.semihbkgr.gorun.message;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Message {

    /*
     * Message format
     * [action:body]
     * [action]
     */


    public final Action action;
    public final String body;

    public Message(Action action, String body) {
        this.action = action;
        this.body = body;
    }

    public static Message of(@NonNull Action action, @NonNull String body) {
        return new Message(Objects.requireNonNull(action), Objects.requireNonNull(body));
    }

    public static Message of(@NonNull Action action) {
        return new Message(Objects.requireNonNull(action), null);
    }

}

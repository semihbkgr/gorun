package com.semihbg.gorun.message;

import static com.semihbg.gorun.message.MessageConstant.*;

public class MessageUtils {

    public static Message unmarshall(String data) {
        data = data.trim();
        if (data.startsWith(MESSAGE_BEGIN_CHARACTER) && data.endsWith(MESSAGE_END_CHARACTER)) {
            data = data.substring(1, data.length() - 1);
            if (data.contains(MESSAGE_COMMAND_BODY_SEPARATOR)) {
                int index = data.indexOf(MESSAGE_COMMAND_BODY_SEPARATOR);
                String commandString = data.substring(0, index);
                String body = data.substring(index + 1);
                try {
                    Command command = Command.of(commandString, true);
                    if (body.equalsIgnoreCase("null") || body.isEmpty())
                        return Message.of(command);
                    else
                        return Message.of(command, body);
                } catch (Exception ignore) {
                    throw new MessageMarshallException(String.format("Illegal command field, command : %s", commandString));
                }
            } else throw new MessageMarshallException(
                    String.format("Missing command body separator, separator = %s",
                            MESSAGE_COMMAND_BODY_SEPARATOR));
        } else throw new MessageMarshallException(
                String.format("Missing message start,end character, start : %s, end : %s",
                        MESSAGE_BEGIN_CHARACTER, MESSAGE_END_CHARACTER));

    }

    public static String marshall(Message message) {
        StringBuilder unmarshallStringBuilder = new StringBuilder();
        unmarshallStringBuilder.append(MESSAGE_BEGIN_CHARACTER);
        String commandString = message.command.name();
        unmarshallStringBuilder.append(commandString);
        unmarshallStringBuilder.append(MESSAGE_COMMAND_BODY_SEPARATOR);
        unmarshallStringBuilder.append(message.body);
        unmarshallStringBuilder.append(MESSAGE_END_CHARACTER);
        return unmarshallStringBuilder.toString();
    }

    public static class MessageMarshallException extends RuntimeException {
        public MessageMarshallException(String message) {
            super(message);
        }
    }

}

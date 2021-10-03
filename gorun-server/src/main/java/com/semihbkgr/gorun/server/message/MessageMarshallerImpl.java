package com.semihbkgr.gorun.server.message;

import org.springframework.stereotype.Component;

import static com.semihbkgr.gorun.server.message.MessageConstants.*;

@Component
public class MessageMarshallerImpl implements MessageMarshaller {

    @Override
    public Message unmarshall(String data) throws MessageMarshallException {
        data = data.strip();
        if (data.startsWith(MESSAGE_BEGIN_CHARACTER) && data.endsWith(MESSAGE_END_CHARACTER)) {
            data = data.substring(1, data.length() - 1);
            if (data.contains(MESSAGE_COMMAND_BODY_SEPARATOR)) {
                int index = data.indexOf(MESSAGE_COMMAND_BODY_SEPARATOR);
                String commandString = data.substring(0, index);
                String body = data.substring(index + 1);
                try {
                    Action action = Action.of(commandString, false);
                    if (body.equalsIgnoreCase("null") || body.isEmpty())
                        return Message.of(action);
                    else
                        return Message.of(action, body);
                } catch (Exception ignore) {
                    throw new MessageMarshallException(String.format("Illegal action field, action : %s", commandString));
                }
            } else throw new MessageMarshallException(
                    String.format("Missing action body separator, separator = %s",
                            MESSAGE_COMMAND_BODY_SEPARATOR));
        } else throw new MessageMarshallException(
                String.format("Missing message start,end character, start : %s, end : %s",
                        MESSAGE_BEGIN_CHARACTER, MESSAGE_END_CHARACTER));

    }

    @Override
    public String marshall(Message message) throws IllegalArgumentException {
        if (!message.action.isInResponse)
            throw new MessageMarshallException(String.format("Illegal action field, action : %s", message.action.name()));
        StringBuilder unmarshallStringBuilder = new StringBuilder();
        unmarshallStringBuilder.append(MESSAGE_BEGIN_CHARACTER);
        String commandString = message.action.name();
        unmarshallStringBuilder.append(commandString);
        unmarshallStringBuilder.append(MESSAGE_COMMAND_BODY_SEPARATOR);
        unmarshallStringBuilder.append(message.body);
        unmarshallStringBuilder.append(MESSAGE_END_CHARACTER);
        return unmarshallStringBuilder.toString();
    }

}

package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.exception.MessageMarshallException;
import com.semihbkgr.gorun.server.message.Command;
import com.semihbkgr.gorun.server.message.Message;
import org.springframework.stereotype.Component;

import static com.semihbkgr.gorun.server.message.MessageConstants.*;

@Component
public class MessageMarshallerImpl implements MessageMarshaller {

    public Message unmarshall(String data, boolean isInResponse) throws MessageMarshallException {
        data = data.strip();
        if (data.startsWith(MESSAGE_BEGIN_CHARACTER) && data.endsWith(MESSAGE_END_CHARACTER)) {
            data = data.substring(1, data.length() - 1);
            if (data.contains(MESSAGE_COMMAND_BODY_SEPARATOR)) {
                int index = data.indexOf(MESSAGE_COMMAND_BODY_SEPARATOR);
                String commandString = data.substring(0, index);
                String body = data.substring(index + 1);
                try {
                    Command command = Command.of(commandString, isInResponse);
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

    @Override
    public Message unmarshall(String data) throws MessageMarshallException {
        return this.unmarshall(data, false);
    }

    public String marshall(Message message, boolean isInResponse) {
        if (message.command.isInResponse != isInResponse)
            throw new MessageMarshallException(String.format("Illegal command field, command : %s", message.command.name()));
        StringBuilder unmarshallStringBuilder = new StringBuilder();
        unmarshallStringBuilder.append(MESSAGE_BEGIN_CHARACTER);
        String commandString = message.command.name();
        unmarshallStringBuilder.append(commandString);
        unmarshallStringBuilder.append(MESSAGE_COMMAND_BODY_SEPARATOR);
        unmarshallStringBuilder.append(message.body);
        unmarshallStringBuilder.append(MESSAGE_END_CHARACTER);
        return unmarshallStringBuilder.toString();
    }

    @Override
    public String marshall(Message message) {
        return this.marshall(message, true);
    }

}

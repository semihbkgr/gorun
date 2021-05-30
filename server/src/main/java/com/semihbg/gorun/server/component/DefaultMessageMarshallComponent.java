package com.semihbg.gorun.server.component;

import com.semihbg.gorun.server.exception.MessageMarshallException;
import com.semihbg.gorun.server.message.Command;
import com.semihbg.gorun.server.message.Message;
import org.springframework.stereotype.Component;

import static com.semihbg.gorun.server.message.MessageConstants.*;

@Component
public class DefaultMessageMarshallComponent implements MessageMarshallComponent {

    @Override
    public Message marshall(String data) throws MessageMarshallException {
        data = data.strip();
        if (data.startsWith(MESSAGE_BEGIN_CHARACTER) && data.endsWith(MESSAGE_END_CHARACTER)) {
            data = data.substring(1, data.length() - 1);
            if (data.contains(MESSAGE_COMMAND_BODY_SEPARATOR)) {
                int index = data.indexOf(MESSAGE_COMMAND_BODY_SEPARATOR);
                String commandString = data.substring(0, index);
                String body = data.substring(index + 1);
                try {
                    Command command = Command.of(commandString, false);
                    if (body.equalsIgnoreCase("null") || body.isEmpty() || body.isBlank())
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
    public String unmarshall(Message message) {
        StringBuilder unmarshallStringBuilder = new StringBuilder();
        unmarshallStringBuilder.append(MESSAGE_BEGIN_CHARACTER);
        String commandString = message.command.name();
        unmarshallStringBuilder.append(commandString);
        unmarshallStringBuilder.append(MESSAGE_COMMAND_BODY_SEPARATOR);
        unmarshallStringBuilder.append(message.body);
        unmarshallStringBuilder.append(MESSAGE_END_CHARACTER);
        return unmarshallStringBuilder.toString();
    }


}

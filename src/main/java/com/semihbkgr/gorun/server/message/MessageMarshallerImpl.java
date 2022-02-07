package com.semihbkgr.gorun.server.message;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import static com.semihbkgr.gorun.server.message.MessageConstants.*;

@Component
public class MessageMarshallerImpl implements MessageMarshaller {

    @Override
    public Message unmarshall(@NonNull String data) throws MessageMarshallException {
        if (data.startsWith(MESSAGE_BEGINNING_CHARACTER) && data.endsWith(MESSAGE_END_CHARACTER)) {
            if (data.contains(MESSAGE_COMMAND_BODY_SEPARATOR)) {
                // has a body
                int index = data.indexOf(MESSAGE_COMMAND_BODY_SEPARATOR);
                var actionStr = data.substring(1, index);
                var body = data.substring(index + 1, data.length() - 1);
                try {
                    var action = Action.valueOf(actionStr);
                    return Message.of(action, body);
                } catch (Exception e) {
                    throw new MessageMarshallException("Illegal action in message, action: " + actionStr, e);
                }
            } else {
                // has no body
                var actionStr = data.substring(1, data.length() - 1);
                try {
                    var action = Action.valueOf(actionStr);
                    return Message.of(action);
                } catch (Exception e) {
                    throw new MessageMarshallException("Illegal action in message, action: " + actionStr, e);
                }
            }
        } else throw new MessageMarshallException(
                String.format("Illegal message format, message must begin with '%s' and end with '%s' characters",
                        MESSAGE_BEGINNING_CHARACTER, MESSAGE_END_CHARACTER));
    }

    @Override
    public String marshall(@NonNull Message message) throws IllegalArgumentException {
        if (message.body != null)
            // has a body
            return MESSAGE_BEGINNING_CHARACTER +
                    message.action.name() +
                    MESSAGE_COMMAND_BODY_SEPARATOR +
                    message.body +
                    MESSAGE_END_CHARACTER;
        else
            // has no body
            return MESSAGE_BEGINNING_CHARACTER +
                    message.action.name() +
                    MESSAGE_END_CHARACTER;
    }

}

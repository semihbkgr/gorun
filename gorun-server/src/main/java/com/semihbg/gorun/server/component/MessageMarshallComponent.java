package com.semihbg.gorun.server.component;

import com.semihbg.gorun.server.exception.MessageMarshallException;
import com.semihbg.gorun.server.message.Message;

public interface MessageMarshallComponent {

    Message unmarshall(String data) throws MessageMarshallException;

    String marshall(Message message) throws MessageMarshallException;

}

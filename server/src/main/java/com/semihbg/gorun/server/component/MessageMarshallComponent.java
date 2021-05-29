package com.semihbg.gorun.server.component;

import com.semihbg.gorun.server.exception.MessageMarshallException;
import com.semihbg.gorun.server.message.Message;

public interface MessageMarshallComponent {

    Message marshall(String data) throws MessageMarshallException;

    String unmarshall(Message message) throws MessageMarshallException;

}

package com.semihbkgr.gorun.server.component;

import com.semihbkgr.gorun.server.exception.MessageMarshallException;
import com.semihbkgr.gorun.server.message.Message;

public interface MessageMarshallComponent {

    Message unmarshall(String data) throws MessageMarshallException;

    String marshall(Message message) throws MessageMarshallException;

}

package com.semihbkgr.gorun.message;

public interface MessageMarshaller {

    Message unmarshall(String data) throws MessageMarshallException;

    String marshall(Message message) throws MessageMarshallException;

}

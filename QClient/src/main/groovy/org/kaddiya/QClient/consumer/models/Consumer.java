package org.kaddiya.QClient.consumer.models;


import org.kaddiya.QClient.common.Message;

public interface Consumer<T> {

    public void registerSubscription();

    public Message consumeMessage();

    public void registerAckFor(Message m);
}

package org.kaddiya.QServer.internal.services;


import org.kaddiya.QClient.common.Message;
import org.kaddiya.QClient.consumer.models.RegistrationException;


public interface TopicPutter {

    public boolean putInTopic(String topicId, Message m);

    public void registerSubscription(String topicId, String consumerId) throws RegistrationException;
}

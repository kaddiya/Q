package org.kaddiya.QServer.internal.services;


import org.kaddiya.QClient.common.Message;
import org.kaddiya.QClient.consumer.models.RegistrationException;

import java.util.List;


public interface TopicPutter {

    public boolean putInTopic(String topicId, Message m);

    public void registerSubscription(String topicId, String consumerId, List<String> consumerDependencies) throws RegistrationException;

    public Message readMessageFromTopic(String topicId,String consumerId);
}

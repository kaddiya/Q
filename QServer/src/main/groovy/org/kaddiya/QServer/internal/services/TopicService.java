package org.kaddiya.QServer.internal.services;


import org.kaddiya.QClient.common.Message;
import org.kaddiya.QServer.internal.models.exceptions.DuplicateRegistrationException;
import org.kaddiya.QServer.internal.models.exceptions.UnRegisteredException;

import java.util.List;
import java.util.UUID;


public interface TopicService {

    public boolean putInTopic(String topicId, Message m);

    public void registerSubscription(String topicId, String consumerId, List<String> consumerDependencies) throws DuplicateRegistrationException;

    public Message readMessageFromTopic(String topicId,String consumerId) throws UnRegisteredException;

    public void registerAckFor(UUID messageId,String topicId,String consumerId) throws UnRegisteredException;
}

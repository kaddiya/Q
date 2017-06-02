package org.kaddiya.QServer.internal.services

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message
import org.kaddiya.QServer.internal.models.Datastore
import org.kaddiya.QServer.internal.models.exceptions.DependantException
import org.kaddiya.QServer.internal.models.exceptions.DuplicateRegistrationException
import org.kaddiya.QServer.internal.models.exceptions.UnRegisteredException
import org.restlet.resource.ResourceException

@Slf4j
@CompileStatic
public class TopicServiceImpl implements TopicService {
    @Override
    public boolean putInTopic(String topicId, Message m) {

        try {
            Datastore.addMessageToTopic(topicId, m)
        } catch (IllegalStateException e) {
            throw new ResourceException(507, "The queue is full.The message will be published when capacity is freed up")
        }

    }

    @Override
    void registerSubscription(String topicId, String consumerId, List<String> consumerDependencies) throws DuplicateRegistrationException {
        Datastore.registerSubscription(topicId, consumerId, consumerDependencies)
    }

    @Override
    Message readMessageFromTopic(String topicId, String consumerId) throws UnRegisteredException {
        try {
            return Datastore.getMessage(topicId, consumerId)
        } catch (NoSuchElementException e) {
            throw new ResourceException(404, "No current Message is available for topic" + topicId)
        } catch (DependantException e) {
            throw new ResourceException(206, e.getMessage())
        }


    }

    @Override
    void registerAckFor(UUID messageId, String topicId, String consumerId) throws UnRegisteredException {
        Datastore.registerAckfor(messageId, topicId, consumerId)
    }
}

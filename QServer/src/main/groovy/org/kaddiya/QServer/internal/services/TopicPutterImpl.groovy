package org.kaddiya.QServer.internal.services

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message
import org.kaddiya.QServer.internal.models.Datastore
import org.restlet.resource.ResourceException

@Slf4j
@CompileStatic
public class TopicPutterImpl implements TopicPutter {
    @Override
    public boolean putInTopic(String topicId, Message m) {

        try {
            Datastore.addMessageToTopic(topicId, m)
        } catch (IllegalStateException e) {
            log.error("error occured while publishing the message", e)
            throw new ResourceException(507, "The queue is full.The message will be published when capacity is freed up")
        }

    }
}

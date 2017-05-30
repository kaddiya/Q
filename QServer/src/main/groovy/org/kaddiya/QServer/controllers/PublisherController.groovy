package org.kaddiya.QServer.controllers

import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.producer.models.PublishRequest
import org.kaddiya.QServer.internal.services.TopicPutter
import org.restlet.resource.Post
import org.restlet.resource.ResourceException
import org.restlet.resource.ServerResource

@Slf4j
@CompileStatic
class PublisherController extends ServerResource {

    @Inject
    TopicPutter putter

    @Post
    public Boolean publishMessage(PublishRequest req) {
        String topicId = req.getTopicName()
        String content = req.getMessage()
        Message m = new Message(UUID.randomUUID(), content)
        if (topicId == null) {
            throw new ResourceException(400, "topic id missing")
        }
        putter.putInTopic(topicId, m)
        return true;
    }
}

package org.kaddiya.QServer.controllers

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.models.Message
import org.kaddiya.QClient.models.PublishRequest
import org.kaddiya.QServer.internal.services.TopicPutter
import org.restlet.resource.Post
import org.restlet.resource.ResourceException
import org.restlet.resource.ServerResource

/**
 * Created by Webonise on 28/05/17.
 */
@Slf4j
class PublisherController extends ServerResource {

    @Inject
    TopicPutter putter

    @Post
    public Boolean publishMessage(PublishRequest req) {
        log.info("got the request!")
        String topicId = req.getTopicName()
        String content = req.getMessage()
        log.info(content.toString())
        Message m = new Message(UUID.randomUUID(), content)
        if (topicId == null) {
            throw new ResourceException(400, "topic id missing")
        }
        putter.putInTopic(topicId, m)
        return true;
    }
}

package org.kaddiya.QServer.controllers

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.models.Message
import org.kaddiya.QClient.models.PublishRequest
import org.kaddiya.QServer.internal.models.Datastore
import org.kaddiya.QServer.internal.services.TopicPutter
import org.restlet.resource.Post
import org.restlet.resource.ServerResource

/**
 * Created by Webonise on 28/05/17.
 */
@Slf4j
class PublisherController extends ServerResource {

    @Inject
    TopicPutter putter

    @Post
    public Boolean publishMessage(PublishRequest req){
        String topicId = req.getTopicName()
        Message m = req.getMessage()
        putter.putInTopic(topicId,m)
        return true;
    }
}

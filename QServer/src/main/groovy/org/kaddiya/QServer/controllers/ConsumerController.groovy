package org.kaddiya.QServer.controllers

import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message
import org.kaddiya.QServer.internal.services.TopicPutterImpl
import org.restlet.resource.Get
import org.restlet.resource.ServerResource

@Slf4j
@CompileStatic
class ConsumerController extends ServerResource {


    @Inject
    TopicPutterImpl topicPutterImpl;

    @Get
    public Message getMessage() {
        String topicId = request.getAttributes().get("topicId")
        log.info("getting message from topic ID " + topicId)
        return topicPutterImpl.readMessageFromTopic(topicId)

    }


}

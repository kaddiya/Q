package org.kaddiya.QServer.controllers

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message
import org.kaddiya.QServer.internal.models.Datastore
import org.restlet.resource.Get
import org.restlet.resource.ServerResource

@Slf4j
@CompileStatic
class ConsumerController extends ServerResource {


    @Get
    public Message getMessage() {
        String topicId = request.getAttributes().get("topicId")
        Message message = Datastore.getMessage(topicId);
        return message
    }



}

package org.kaddiya.QServer.controllers

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message
import org.kaddiya.QServer.internal.models.Datastore
import org.kaddiya.QServer.internal.models.Topic
import org.restlet.resource.Get
import org.restlet.resource.ServerResource

/**
 * Created by Webonise on 28/05/17.
 */
@Slf4j
@CompileStatic
class ConsumerController extends ServerResource {


    @Get
    public Message getMessage() {
        String s = request.getAttributes().get("topicId")
        Message message = Datastore.getMessage(s);
        return message
    }


}

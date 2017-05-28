package org.kaddiya.QServer.controllers

import groovy.util.logging.Slf4j
import org.kaddiya.QServer.internal.models.Datastore
import org.kaddiya.QServer.internal.models.Topic
import org.restlet.resource.Get
import org.restlet.resource.ServerResource

/**
 * Created by Webonise on 28/05/17.
 */
@Slf4j
class ConsumerController extends ServerResource {


    @Get
    public String getMessage() {
        String s = request.getAttributes().get("topicId")
        Topic t = Datastore.getTopicById(s);
        String mes = t.queue.take()
        return mes
    }


}

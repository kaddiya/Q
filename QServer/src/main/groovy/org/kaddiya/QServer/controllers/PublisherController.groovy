package org.kaddiya.QServer.controllers

import groovy.util.logging.Slf4j
import org.restlet.resource.Post
import org.restlet.resource.ServerResource

/**
 * Created by Webonise on 28/05/17.
 */
@Slf4j
class PublisherController extends ServerResource {

    @Post
    public Boolean publishMessage(){
        log.info("Publishing a message")
        return true;
    }
}

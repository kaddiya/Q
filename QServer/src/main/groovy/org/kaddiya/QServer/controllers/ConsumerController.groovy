package org.kaddiya.QServer.controllers

import org.restlet.resource.Get
import org.restlet.resource.Post
import org.restlet.resource.ServerResource

/**
 * Created by Webonise on 28/05/17.
 */
class ConsumerController extends ServerResource {


    @Get
    public String getMessage(){
        return "getting you the message"
    }


}

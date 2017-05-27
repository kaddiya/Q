package org.kaddiya.QServer.controllers

import org.restlet.Server
import org.restlet.resource.Get
import org.restlet.resource.ServerResource

/**
 * Created by Webonise on 27/05/17.
 */
class PingController  extends ServerResource{


    @Get
    public boolean ping(){
        return pong
    }
}

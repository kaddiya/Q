package org.kaddiya.QServer.controllers

import org.restlet.resource.Get
import org.restlet.resource.ServerResource


class PingController extends ServerResource {


    @Get
    public boolean ping() {
        return "pong"
    }
}

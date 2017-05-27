package org.kaddiya.QServer.routers

import org.kaddiya.QServer.controllers.PingController
import restling.restlet.RestlingRouter


class RootRouter extends RestlingRouter {

    @Override
    void init() throws Exception {
        attach("/ping",PingController)
    }
}

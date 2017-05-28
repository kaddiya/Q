package org.kaddiya.QServer.routers

import org.kaddiya.QServer.controllers.ConsumerController
import org.kaddiya.QServer.controllers.PublisherController
import org.kaddiya.QServer.controllers.PingController
import restling.restlet.RestlingRouter


class RootRouter extends RestlingRouter {

    @Override
    void init() throws Exception {
        attach("/ping",PingController)
        attach("/producer",PublisherController)
        attach("/consumer",ConsumerController)
    }
}

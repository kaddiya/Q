package org.kaddiya.QServer.routers

import org.kaddiya.QServer.controllers.ConsumerController
import org.kaddiya.QServer.controllers.ConsumerRegistrationController
import org.kaddiya.QServer.controllers.PingController
import org.kaddiya.QServer.controllers.PublisherController
import restling.restlet.RestlingRouter

class RootRouter extends RestlingRouter {

    @Override
    void init() throws Exception {
        attach("/ping", PingController)
        attach("/producer", PublisherController)
        attach("/consumer_registration", ConsumerRegistrationController)
        attach("/consumer/{consumerId}/{topicId}", ConsumerController)
    }
}

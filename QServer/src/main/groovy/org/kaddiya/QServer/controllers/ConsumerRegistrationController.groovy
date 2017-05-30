package org.kaddiya.QServer.controllers

import groovy.util.logging.Slf4j
import org.kaddiya.QClient.consumer.models.SubscriptionRegistrationRequest
import org.restlet.data.Status
import org.restlet.resource.Post
import org.restlet.resource.ServerResource

@Slf4j
class ConsumerRegistrationController extends ServerResource {

    @Post
    public Status registerSubscription(SubscriptionRegistrationRequest request) {
        log.info("got the request to register to topic {}  by {}", request.topicId, request.consumerId)
        return Status.SUCCESS_OK
    }


}

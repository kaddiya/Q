package org.kaddiya.QServer.controllers

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.consumer.models.RegistrationException
import org.kaddiya.QClient.consumer.models.SubscriptionRegistrationRequest
import org.kaddiya.QServer.internal.services.TopicPutterImpl
import org.restlet.data.Status
import org.restlet.resource.Post
import org.restlet.resource.ResourceException
import org.restlet.resource.ServerResource

@Slf4j
class ConsumerRegistrationController extends ServerResource {

    @Inject
    TopicPutterImpl topicPutterImpl

    @Post
    public Status registerSubscription(SubscriptionRegistrationRequest request) {
        log.info("got the request to register to topic {}  by {}", request.topicId, request.consumerId)
        try {
            topicPutterImpl.registerSubscription(request.topicId, request.consumerId, request.depdenciesOfConsumers)
        } catch (RegistrationException e) {
            log.error("Recieved a duplicate request for registration", e)
            throw new ResourceException(409, "Duplicate registration request received")

        }
        return Status.SUCCESS_OK
    }


}

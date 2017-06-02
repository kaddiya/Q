package org.kaddiya.QServer.controllers

import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.consumer.models.AckRequest
import org.kaddiya.QServer.internal.models.exceptions.UnRegisteredException
import org.kaddiya.QServer.internal.services.TopicServiceImpl
import org.restlet.data.Status
import org.restlet.resource.Get
import org.restlet.resource.Post
import org.restlet.resource.ResourceException
import org.restlet.resource.ServerResource

@Slf4j
@CompileStatic
class ConsumerController extends ServerResource {


    @Inject
    TopicServiceImpl topicPutterImpl;

    @Get
    public Message getMessage() {
        String topicId = request.getAttributes().get("topicId")
        String consumerId = request.getAttributes().get("consumerId")
        try{
            Message m=topicPutterImpl.readMessageFromTopic(topicId,consumerId)
            return m
        }catch (UnRegisteredException e){
            throw new ResourceException(403,"The consumer is not authorised to subscribe")
        }
    }

    @Post
    public Status registerAck(AckRequest ackRequest){
        String topicId = request.getAttributes().get("topicId")
        String consumerId = request.getAttributes().get("consumerId")

        try{
            topicPutterImpl.registerAckFor(ackRequest.messageId,topicId,consumerId);
            return Status.REDIRECTION_PERMANENT
        }catch (UnRegisteredException e){
            throw new ResourceException(403,"The consumer is not authorised to subscribe")
        }


    }


}

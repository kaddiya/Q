package org.kaddiya.QServer.internal.models

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

@Slf4j
@CompileStatic
public class ConsumerMultiplexer implements Observer {

    private final LinkedBlockingQueue<Message> consumerBuffer

    private String consumerId;
    private String topicId;
    public ConsumerMultiplexer (String consumerId,String topicId,LinkedBlockingQueue<Message> buffer){
        this.topicId = topicId;
        this.consumerId = consumerId;
        this.consumerBuffer = buffer;
        //log.info("A consumer multiplexer is setup for consumer "+consumerId+" and topic "+topicId)
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            consumerBuffer.add((Message)arg)
        } catch (InterruptedException e) {
            log.error("Could not cache the message for conusmerId"+consumerId)
        }
    }

    public Message getMessage(){
        try {
            Message m = consumerBuffer.remove();
            return m
        }catch (InterruptedException e) {
            log.error("Could not get the latest message for consumerId"+consumerId)
        }

    }
}

package org.kaddiya.QServer.internal.models

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

@Slf4j
@CompileStatic
public class ConsumerMultiplexer implements Observer {

    private LinkedBlockingQueue<Message> consumerBuffer = new LinkedBlockingQueue<Message>();

    private String consumerId;
    private String topicId;
    public ConsumerMultiplexer (String consumerId,String topicId){
        this.topicId = topicId;
        this.consumerId = consumerId;
        log.info("A consumer multiplexer is setup for consumer "+consumerId+" and topic "+topicId)
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            log.info("Added the message to"+consumerId+"buffer")
        } catch (InterruptedException e) {
            log.error("Could not cache the message for conusmerId"+consumerId)
        }
    }

    public Message getMessage(){
        try {
            Message m = consumerBuffer.remove();
            log.info("Consumed the message to"+consumerId+"buffer")
            return m
        }catch (InterruptedException e) {
            log.error("Could not get the latest message for consumerId"+consumerId)
        }

    }
}

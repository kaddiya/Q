package org.kaddiya.QServer.internal.models

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message

import java.util.concurrent.LinkedBlockingQueue

@Slf4j
@CompileStatic
public class ConsumerMultiplexer implements Observer {

    private final LinkedBlockingQueue<Message> consumerBuffer

    private String consumerId;
    private String topicId;
    private Map<UUID, MessageStatus> deliveryLog = new HashMap<UUID, MessageStatus>();

    public ConsumerMultiplexer(String consumerId, String topicId, LinkedBlockingQueue<Message> buffer) {
        this.topicId = topicId;
        this.consumerId = consumerId;
        this.consumerBuffer = buffer;

    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            deliveryLog.put((arg as Message).uuid, MessageStatus.RCVD)
            consumerBuffer.add((Message) arg)
        } catch (InterruptedException e) {
            log.error("Could not cache the message for conusmerId" + consumerId)
        }
    }

    public Message getMessage() {
        try {
            Message m = consumerBuffer.peek();
            if (m != null) {
                deliveryLog.put(m.uuid, MessageStatus.READ)
                return m
            }
        } catch (InterruptedException e) {
            log.error("Could not get the latest message for consumerId" + consumerId)
        }

    }

    public void ackMessage(UUID messageId) {
        deliveryLog.put(messageId, MessageStatus.ACK)
        log.info("consumer id " + consumerId + " has Acknowledge this message " + messageId)
        consumerBuffer.remove();
    }

    public Boolean isAcked(UUID messageId) {
        return deliveryLog.get(messageId) == MessageStatus.ACK;
    }
}

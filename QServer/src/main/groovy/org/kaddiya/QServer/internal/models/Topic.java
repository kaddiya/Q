package org.kaddiya.QServer.internal.models;


import org.kaddiya.QClient.common.Message;
import org.kaddiya.QServer.internal.models.exceptions.DependantException;
import org.kaddiya.QServer.internal.models.exceptions.DuplicateRegistrationException;
import org.kaddiya.QServer.internal.models.exceptions.UnRegisteredException;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Topic extends Observable {
    private String topicId;
    //every topic should have
    // the queue representation
    private ArrayBlockingQueue queue = new ArrayBlockingQueue<Message>(10, true);
    //master list of all consumer
    private Map<UUID, MessageStatus> recievingLog = new HashMap<UUID, MessageStatus>();
    //multiplexer for each consumer
    private Map<String, ConsumerMultiplexer> consumerMap = new HashMap<String, ConsumerMultiplexer>();
    //subscriptions
    private Map<String, List<String>> subscriptions = new Hashtable<String, List<String>>();
    public Topic(String topicId) {
        this.topicId = topicId;
    }

    public synchronized void addMessageToQueue(Message m) {
        //add message to queue
        this.queue.add(m);
        setChanged();
        notifyObservers(m);

    }

    public synchronized void registerSubscriptions(String consumerId, List<String> consumerDependencies) throws DuplicateRegistrationException {
        if (subscriptions.containsKey(consumerId)) {
            //already registered
            throw new DuplicateRegistrationException("A registration already exists");
        }
        //register the subscription
        this.subscriptions.put(consumerId, consumerDependencies);
        //multiplex the queue
        LinkedBlockingQueue<Message> history = new LinkedBlockingQueue<Message>(queue);
        ConsumerMultiplexer consumerBuffer = new ConsumerMultiplexer(consumerId, topicId, history);
        this.addObserver(consumerBuffer);
        this.consumerMap.put(consumerId, consumerBuffer);
    }

    public synchronized Message consumeMessage(String consumerId) throws UnRegisteredException, DependantException {
        if (!consumerMap.containsKey(consumerId)) {
            throw new UnRegisteredException("Could not find registration");
        }
        Message tempMessage = consumerMap.get(consumerId).getMessage();

        List<String> deps = subscriptions.get(consumerId);
        for (String a : deps) {
            if (consumerMap.get(a) != null && !consumerMap.get(a).isAcked(tempMessage.getUuid())) {
                throw new DependantException("A depenant consumer with id" + a + " has not yet consumed this message");
            }
        }
        return tempMessage;
    }

    public synchronized void registerAck(UUID messageId, String consumerId) throws UnRegisteredException {
        if (!consumerMap.containsKey(consumerId)) {
            throw new UnRegisteredException("Could not find registration");
        }
        ConsumerMultiplexer buffer = consumerMap.get(consumerId);
        buffer.ackMessage(messageId);
        try {
            //the very first ack will mean that the buffers have the data.We can safely remove this
            queue.remove();
        } catch (NoSuchElementException e) {
            //swallow this exception
        }


    }


}

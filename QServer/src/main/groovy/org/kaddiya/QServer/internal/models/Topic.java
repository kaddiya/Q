package org.kaddiya.QServer.internal.models;


import org.kaddiya.QClient.common.Message;
import org.kaddiya.QClient.consumer.models.RegistrationException;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;


public class Topic  {

    //every topic should have
    // the queue representation
    private ArrayBlockingQueue queue = new ArrayBlockingQueue<Message>(5, true);
    // consumer configuration -> consumers details
    //message delivery log -> will have details for each message's delivery and ack
    /// private Set<String,List<String>> subscriptions = new HashSet<String>();

    private Map<String, List<String>> subscriptions = new Hashtable<String, List<String>>();

    public synchronized Queue<Message> getQueue() {
        return this.queue;
    }

    public synchronized Map<String, List<String>> getSubscriptions() {
        return this.subscriptions;
    }

    public synchronized void addMessageToQueue(Message m)  {
            this.queue.add(m);
    }

    public  synchronized void registerSubscriptions(String consumerId,List<String>consumerDependencies) throws RegistrationException {
        if (subscriptions.containsKey(consumerId)) {
            throw new RegistrationException("Consumer is already registered");
        }
        this.subscriptions.put(consumerId, consumerDependencies);
    }

    public synchronized Message consumeMessage(String consumerId){
        return (Message)queue.remove();
    }

}

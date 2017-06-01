package org.kaddiya.QServer.internal.models;


import org.kaddiya.QClient.common.Message;
import org.kaddiya.QClient.consumer.models.RegistrationException;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class Topic extends Observable {
    private String topicId;

    public Topic(String topicId){
        this.topicId=topicId;
    }
    //every topic should have
    // the queue representation
    private ArrayBlockingQueue queue = new ArrayBlockingQueue<Message>(5, true);

    //multiplexer for each consumer
    private List<Observer> consumerMultiplexer = new ArrayList<Observer>();
    //master list of all consumer
    private Map<String,ConsumerMultiplexer> consumerMap = new HashMap<String, ConsumerMultiplexer>();
    //subscriptions
    private Map<String, List<String>> subscriptions = new Hashtable<String, List<String>>();


    public synchronized Map<String, List<String>> getSubscriptions() {
        return this.subscriptions;
    }

    public synchronized void addMessageToQueue(Message m)  {
            this.queue.add(m);
            setChanged();
            notifyObservers(m);
    }

    public  synchronized void registerSubscriptions(String consumerId,List<String>consumerDependencies) throws RegistrationException {
        if (subscriptions.containsKey(consumerId)) {
            throw new RegistrationException("Consumer is already registered");
        }
        //register the subscription
        this.subscriptions.put(consumerId, consumerDependencies);
        //multiplex the queue
        ConsumerMultiplexer consumerBuffer = new ConsumerMultiplexer(consumerId,topicId);
        this.consumerMultiplexer.add(consumerBuffer);
        this.consumerMap.put(consumerId,consumerBuffer);
    }

    public synchronized Message consumeMessage(String consumerId){
        /*if(this.consumerMap.get(consumerId) !=null) {
            return this.consumerMap.get(consumerId).getMessage();
        }*/
        return (Message)queue.remove();
    }

}

package org.kaddiya.QServer.internal.models;


import org.kaddiya.QClient.common.Message;
import org.kaddiya.QClient.consumer.models.RegistrationException;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;


public class Topic extends Observable {
    private String topicId;

    public Topic(String topicId){
        this.topicId=topicId;
    }
    //every topic should have
    // the queue representation
    private ArrayBlockingQueue queue = new ArrayBlockingQueue<Message>(5, true);

    //multiplexer for each consumer
    private List<Observer> consumers = new ArrayList<Observer>();
    //master list of all consumer
    private Map<String,ConsumerMultiplexer> consumerMap = new HashMap<String, ConsumerMultiplexer>();
    //subscriptions
    private Map<String, List<String>> subscriptions = new Hashtable<String, List<String>>();

    //this is the delivery /audit log
    private Map<UUID,Map<String,MessageStatus>> deliveryLog = new HashMap<UUID, Map<String,MessageStatus>>();



    public synchronized Map<String, List<String>> getSubscriptions() {
        return this.subscriptions;
    }

    public synchronized void addMessageToQueue(Message m)  {
            //add message to queue
            this.queue.add(m);
            //log it in the delivery log
            setChanged();
            notifyObservers(m);
    }

    public  synchronized void registerSubscriptions(String consumerId,List<String>consumerDependencies) throws RegistrationException {
        if (subscriptions.containsKey(consumerId)) {
            throw new RegistrationException("Consumer is already registered");
        }
        //register the subscription
        this.subscriptions.put(consumerId, consumerDependencies);
        //change the delivery log
        //multiplex the queue
        LinkedBlockingQueue<Message> history = new LinkedBlockingQueue<Message>(queue);
        ConsumerMultiplexer consumerBuffer = new ConsumerMultiplexer(consumerId,topicId,history);
        this.consumers.add(consumerBuffer);
        this.consumerMap.put(consumerId,consumerBuffer);
    }

    public synchronized Message consumeMessage(String consumerId){
        /*if(this.consumerMap.get(consumerId) !=null) {
            return this.consumerMap.get(consumerId).getMessage();
        }*/
        //peek the first element and give it back.
        return consumerMap.get(consumerId).getMessage();
    }

    public synchronized Message registerAck(UUID messageId ,String consumerId){
        //register the ack for the message
        //and then remove
       /* System.out.println("While Ack the ssize is"+queue.size());
        Iterator<Message> it = queue.iterator();
        while (it.hasNext()){
            Message m = it.next();
            System.out.println("head is "+m.getContent());
        }*/
        return (Message) queue.peek();

    }

}

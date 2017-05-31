package org.kaddiya.QServer.internal.models;


import org.kaddiya.QClient.common.Message;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;


public class Topic {

    //every topic should have
    // the queue representation
    private ArrayBlockingQueue queue = new ArrayBlockingQueue<Message>(5, true);
    // consumer configuration -> consumers details
    //message delivery log -> will have details for each message's delivery and ack
    private Set<String> subscriptions = new HashSet<String>();

    public synchronized Queue<Message> getQueue() {
        return this.queue;
    }

    public synchronized Set<String> getSubscriptions() {
        return this.subscriptions;
    }
}

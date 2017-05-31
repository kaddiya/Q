package org.kaddiya.QServer.internal.models;


import org.kaddiya.QClient.common.Message;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class Topic {

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
}

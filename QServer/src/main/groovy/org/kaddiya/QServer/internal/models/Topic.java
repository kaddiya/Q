package org.kaddiya.QServer.internal.models;


import org.kaddiya.QClient.common.Message;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class Topic {

    //every topic should have
    // the queue representation
    private ArrayBlockingQueue queue = new ArrayBlockingQueue<Message>(5, true);
    // consumer configuration -> consumers details
    //message delivery log -> will have details for each message's delivery and ack

    public synchronized Queue<Message> getQueue() {
        return this.queue;
    }
}

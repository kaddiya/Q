package org.kaddiya.QServer.internal.models;


import groovy.transform.CompileStatic;
import groovy.transform.Immutable;
import org.kaddiya.QClient.models.Message;

import java.util.concurrent.ArrayBlockingQueue;


/**
 * Created by Webonise on 27/05/17.
 */

@Immutable
@CompileStatic
public class Topic {

    //every topic should have
    // the queue representation
    public ArrayBlockingQueue queue = new ArrayBlockingQueue<String>(5, true);
    // consumer configuration -> consumers details
    //message delivery log -> will have details for each message's delivery and ack

}

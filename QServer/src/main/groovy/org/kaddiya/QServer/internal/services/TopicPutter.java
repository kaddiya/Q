package org.kaddiya.QServer.internal.services;

import org.kaddiya.QClient.models.Message;

/**
 * Created by Webonise on 28/05/17.
 */
public interface TopicPutter {

    public boolean putInTopic(String topicId,Message m);
}

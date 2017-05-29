package org.kaddiya.QServer.internal.services;


import org.kaddiya.QClient.common.Message;


public interface TopicPutter {

    public boolean putInTopic(String topicId, Message m);
}

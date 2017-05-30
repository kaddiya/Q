package org.kaddiya.QServer.internal.dao;


import org.kaddiya.QClient.common.Message;

public interface TopicDao {
    public Message getMessageFromTopic(String topicId);
    public void addMessageToTopic(String topicId,Message m);
}

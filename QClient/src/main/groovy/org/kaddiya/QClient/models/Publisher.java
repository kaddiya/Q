package org.kaddiya.QClient.models;

/**
 * Created by Webonise on 27/05/17.
 */
public interface Publisher {

    public void publish(Message message,TopicConfig topicConfig);
}

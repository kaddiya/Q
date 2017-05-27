package org.kaddiya.QClient.models;

/**
 * Created by Webonise on 27/05/17.
 */
public interface Subscriber<T> {

    public boolean subscribe(TopicConfig config);

    public T consume();

}

package org.kaddiya.QServer.internal.services;

import groovy.transform.CompileStatic;
import groovy.util.logging.Slf4j;
import org.kaddiya.QClient.models.Message;
import org.kaddiya.QServer.internal.models.Datastore;
import org.kaddiya.QServer.internal.models.Topic;

import java.util.UUID;

/**
 * Created by Webonise on 28/05/17.
 */
@Slf4j
@CompileStatic
public class TopicPutterImpl implements TopicPutter {
    @Override
    public boolean putInTopic(String topicId, Message m) {
        Topic t = Datastore.getTopicById(topicId);
        t.queue.add(UUID.randomUUID().toString());
        return true;
    }
}

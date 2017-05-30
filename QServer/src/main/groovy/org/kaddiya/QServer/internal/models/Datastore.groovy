package org.kaddiya.QServer.internal.models

import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message


@Slf4j
public class Datastore {
    private static Datastore datastore = new Datastore();
    private static volatile Map<String, Topic> topics;

    private Datastore() {
        topics = new HashMap<String, Topic>()
    }

    private static Topic getTopicById(String topicId) {
        if (topics.get(topicId) == null) {
            log.warn("Topic with id {} not found.Creating one silenty", topicId)
            topics.put(topicId, new Topic())
        }
        return topics.get(topicId)

    }

    public static synchronized void addMessageToTopic(String topicId, Message m) {
        Topic t = getTopicById(topicId)
        t.getQueue().add(m)
    }

    public static synchronized Message getMessage(String topicId) {
        return getTopicById(topicId).getQueue().take()
    }
}

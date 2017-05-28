package org.kaddiya.QServer.internal.models

import groovy.util.logging.Slf4j

/**
 * Created by Webonise on 27/05/17.
 */
@Slf4j
public class Datastore {
    private static Datastore datastore = new Datastore();
    private static Map<String, Topic> topics;

    private Datastore() {
        topics = new HashMap<String, Topic>()
    }

    public static Topic getTopicById(String topicId) {
        if (topics.get(topicId) == null) {
            log.warn("Topic with id {} not found.Creating one silenty", topicId)
            topics.put(topicId, new Topic())
        }
        return topics.get(topicId)

    }
}

package org.kaddiya.QServer.internal.models

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.consumer.models.RegistrationException


@Slf4j
@CompileStatic
public class Datastore {
    private static Datastore datastore = new Datastore();
    private static Map<String, Topic> topics;

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

    public Datastore getInstance() {
        return this.datastore
    }

    public synchronized static void addMessageToTopic(String topicId, Message m) {
        Topic t = getTopicById(topicId)
        t.getQueue().add(m)
    }

    public synchronized static Message getMessage(String topicId) {
        Message rsult = getTopicById(topicId).getQueue().remove()
        return rsult;
    }

    public synchronized
    static Boolean registerSubscription(String topicId, String consumerId, List<String> consumerDependencies) {
        Topic t = getTopicById(topicId)
        if (t.getSubscriptions().containsKey(consumerId)) {
            throw new RegistrationException("Consumer is already registered")
        }
        return t.getSubscriptions().put(consumerId, consumerDependencies)

    }
}

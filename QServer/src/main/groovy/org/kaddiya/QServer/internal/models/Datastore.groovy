package org.kaddiya.QServer.internal.models

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.Message

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
            topics.put(topicId, new Topic(topicId))
        }
        return topics.get(topicId)

    }

    public Datastore getInstance() {
        return this.datastore
    }

    public synchronized static void addMessageToTopic(String topicId, Message m) {
        Topic t = getTopicById(topicId)
        t.addMessageToQueue(m);
    }


    public synchronized static Message getMessage(String topicId,String consumerId) {
        Topic t = getTopicById(topicId)
        return t.consumeMessage(consumerId)
    }

    public synchronized static void registerSubscription(String topicId, String consumerId, List<String> consumerDependencies) {
        Topic t = getTopicById(topicId)
        t.registerSubscriptions(consumerId,consumerDependencies)
    }

    public synchronized static void registerAckfor(UUID messageId,String topicId,String consumerId){
        Topic t = getTopicById(topicId)
        t.registerAck(messageId,consumerId)
    }
}

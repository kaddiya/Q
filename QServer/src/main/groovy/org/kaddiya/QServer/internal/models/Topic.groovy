package org.kaddiya.QServer.internal.models

import groovy.transform.Immutable
import org.kaddiya.QClient.models.Message
import org.kaddiya.QClient.models.TopicConfig

/**
 * Created by Webonise on 27/05/17.
 */
@Immutable
class Topic {
    TopicConfig topicConfig;
    Hashtable<String,List<Message>> messages;
}

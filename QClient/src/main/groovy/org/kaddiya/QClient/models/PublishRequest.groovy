package org.kaddiya.QClient.models

import groovy.transform.Immutable

/**
 * Created by Webonise on 28/05/17.
 */
@Immutable
class PublishRequest {
    Message message
    String topicName
}

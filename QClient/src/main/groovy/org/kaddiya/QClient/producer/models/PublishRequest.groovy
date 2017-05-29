package org.kaddiya.QClient.producer.models

import groovy.transform.Immutable

/**
 * Created by Webonise on 28/05/17.
 */
@Immutable
class PublishRequest {
    String message
    String topicName
}

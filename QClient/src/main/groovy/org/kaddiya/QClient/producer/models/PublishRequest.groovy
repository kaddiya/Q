package org.kaddiya.QClient.producer.models

import groovy.transform.Immutable


@Immutable
class PublishRequest {
    String message
    String topicName
}

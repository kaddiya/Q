package org.kaddiya.QClient.models.producer.models

import groovy.transform.Immutable


@Immutable
class Message {
    UUID uuid
    String content
}

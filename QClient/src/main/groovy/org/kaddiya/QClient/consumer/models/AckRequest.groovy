package org.kaddiya.QClient.consumer.models

import groovy.transform.Immutable


@Immutable
class AckRequest {
    UUID messageId
}

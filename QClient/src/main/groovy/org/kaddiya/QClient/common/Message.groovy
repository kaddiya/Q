package org.kaddiya.QClient.common

import groovy.transform.Immutable


@Immutable
class Message {
    UUID uuid
    String content
}

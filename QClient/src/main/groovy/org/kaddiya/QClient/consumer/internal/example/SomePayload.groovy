package org.kaddiya.QClient.consumer.internal.example

import groovy.transform.Canonical

@Canonical
class SomePayload implements Serializable {
    String value
}

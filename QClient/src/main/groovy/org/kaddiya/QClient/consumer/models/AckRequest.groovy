package org.kaddiya.QClient.consumer.models

import groovy.transform.Immutable

/**
 * Created by Webonise on 02/06/17.
 */
@Immutable
class AckRequest {
    UUID uuid
    String consumerId
}

package org.kaddiya.QClient.consumer.models

import groovy.transform.Immutable

/**
 * Created by Webonise on 31/05/17.
 */

@Immutable
class SubscriptionRegistrationRequest {
    String topicId
    String consumerId
}

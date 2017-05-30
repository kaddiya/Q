package org.kaddiya.QClient.consumer.models

import groovy.transform.Immutable

@Immutable
class SubscriptionRegistrationRequest {
    String topicId
    String consumerId
}

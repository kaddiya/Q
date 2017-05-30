package org.kaddiya.QClient.common

import groovy.transform.Immutable


@Immutable
class BrokerConfig {
    String protocol
    String host
    Integer port

}

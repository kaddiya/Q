package org.kaddiya.QClient.common

class BrokerConfig {
    private final String protocol
    private final String host
    private final Integer port

    public BrokerConfig(String protocol, String host, Integer port) {
        this.protocol = protocol
        this.host = host
        this.port = port
    }
}

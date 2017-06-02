package org.kaddiya.QClient.common

class BrokerException extends RetryableException {

    private final String message

    public BrokerException(String message) {
        super(message)
        this.message = message
    }

}

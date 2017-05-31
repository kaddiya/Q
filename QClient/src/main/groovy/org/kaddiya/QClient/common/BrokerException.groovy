package org.kaddiya.QClient.common
/**
 * Created by Webonise on 30/05/17.
 */

class BrokerException extends RetryableException {

    private final String message

    public BrokerException(String message) {
        super(message)
        this.message = message
    }

}

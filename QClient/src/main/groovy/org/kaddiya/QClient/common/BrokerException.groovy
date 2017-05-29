package org.kaddiya.QClient.common
/**
 * Created by Webonise on 30/05/17.
 */

class BrokerException extends Exception {

    private final Integer brokerHttpStatusCode
    private final String message

    public BrokerException(Integer statusCode, String message) {
        this.brokerHttpStatusCode = statusCode
        this.message = message
    }

}

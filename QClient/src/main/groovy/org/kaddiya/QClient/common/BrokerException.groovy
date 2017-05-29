package org.kaddiya.QClient.common
/**
 * Created by Webonise on 30/05/17.
 */

class BrokerException extends Exception {

    private final String message

    public BrokerException(String message) {
        super(message)
        this.message = message
    }

}

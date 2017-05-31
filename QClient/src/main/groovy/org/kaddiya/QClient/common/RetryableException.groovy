package org.kaddiya.QClient.common

/**
 * Created by Webonise on 31/05/17.
 */
class RetryableException extends Exception {

    private String msg

    public RetryableException(String msg) {
        super(msg)
        this.msg = msg
    }
}

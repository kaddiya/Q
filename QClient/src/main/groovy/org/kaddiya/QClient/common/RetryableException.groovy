package org.kaddiya.QClient.common


class RetryableException extends Exception {

    private String msg

    public RetryableException(String msg) {
        super(msg)
        this.msg = msg
    }
}

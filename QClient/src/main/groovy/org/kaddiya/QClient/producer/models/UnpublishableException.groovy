package org.kaddiya.QClient.producer.models


class UnpublishableException extends Exception {

    public UnpublishableException(String message) {
        super(message)
    }

    public UnpublishableException(String message, Throwable cause) {
        super(message, cause)
    }
}

package org.kaddiya.QClient.consumer.models

import org.kaddiya.QClient.common.RetryableException


class ConsumptionException extends RetryableException {
    String message;

    public ConsumptionException(String message) {
        super(message)
        this.message = message
    }
}

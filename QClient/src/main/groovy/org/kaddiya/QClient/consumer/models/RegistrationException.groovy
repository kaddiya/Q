package org.kaddiya.QClient.consumer.models

import org.kaddiya.QClient.common.RetryableException

class RegistrationException extends RetryableException {

    public RegistrationException(String msg) {
        super(msg)
    }
}

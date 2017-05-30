package org.kaddiya.QClient.consumer.models

import groovy.transform.Immutable


class RegistrationException extends Exception {

    public RegistrationException(String msg) {
        super(msg)
    }
}

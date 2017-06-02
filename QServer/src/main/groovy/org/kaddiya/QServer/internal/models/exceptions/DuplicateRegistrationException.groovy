package org.kaddiya.QServer.internal.models.exceptions

/**
 * Created by Webonise on 02/06/17.
 */
class DuplicateRegistrationException extends Exception {
    private final String message

    public DuplicateRegistrationException(String message) {
        this.message = message
    }
}

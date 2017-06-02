package org.kaddiya.QServer.internal.models.exceptions

class DuplicateRegistrationException extends Exception {
    private final String message

    public DuplicateRegistrationException(String message) {
        this.message = message
    }
}

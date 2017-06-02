package org.kaddiya.QServer.internal.models.exceptions


class UnRegisteredException extends Exception {
    private final String message;

    public UnRegisteredException(String message) {
        super(message)
        this.message = message
    }
}

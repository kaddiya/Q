package org.kaddiya.QServer.internal.models.exceptions

/**
 * Created by Webonise on 02/06/17.
 */
class UnRegisteredException extends Exception {
    private final String message;

    public UnRegisteredException(String message) {
        super(message)
        this.message = message
    }
}

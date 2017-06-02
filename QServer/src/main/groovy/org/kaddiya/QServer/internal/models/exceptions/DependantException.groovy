package org.kaddiya.QServer.internal.models.exceptions


class DependantException extends Exception {
    private String message;

    public DependantException(String message){
        super(message)
        this.message = message;
    }
}

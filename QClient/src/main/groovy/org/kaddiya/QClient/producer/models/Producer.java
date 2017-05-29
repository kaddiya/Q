package org.kaddiya.QClient.producer.models;

public interface Producer {

    public void publishToBroker(Object m) throws UnpublishableException;
}

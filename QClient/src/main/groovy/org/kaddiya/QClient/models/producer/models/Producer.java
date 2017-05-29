package org.kaddiya.QClient.models.producer.models;

public interface Producer {

    public void publishToBroker(Object m) throws UnpublishableException;
}

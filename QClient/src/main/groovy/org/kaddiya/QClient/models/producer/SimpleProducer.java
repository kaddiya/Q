package org.kaddiya.QClient.models.producer;

/**
 * Created by Webonise on 28/05/17.
 */
public class SimpleProducer extends BaseProducer implements Producer{

    @Override
    public void publishMe(Object m){
        super.publish(m);
    }
}

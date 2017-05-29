package org.kaddiya.QClient.producer.internal

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.producer.models.Producer
import org.kaddiya.QClient.producer.models.UnpublishableException

@Slf4j
@CompileStatic
public class SimpleProducer extends BaseProducer implements Producer {


    public SimpleProducer(String topicId, BrokerConfig bCfg) {
        super(topicId, bCfg);
    }

    @Override
    public void publishToBroker(Object m) {
        try {
            super.publish(m);
        } catch (Exception e) {
            throw new UnpublishableException(e.getMessage(), e.getCause())
        }
    }
}
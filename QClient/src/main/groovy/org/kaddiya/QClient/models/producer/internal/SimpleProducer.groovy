package org.kaddiya.QClient.models.producer.internal

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.models.producer.models.BrokerConfig
import org.kaddiya.QClient.models.producer.models.Producer

@Slf4j
@CompileStatic
public class SimpleProducer extends BaseProducer implements Producer {


    public SimpleProducer(String topicId, BrokerConfig bCfg) {
        super(topicId, bCfg);
    }

    @Override
    public void publishToBroker(Object m) {
        try {
            log.info("hello!")
            super.publish(m);
        } catch (Exception e) {
            log.error("error occured while publsihing", e)
        }
    }
}
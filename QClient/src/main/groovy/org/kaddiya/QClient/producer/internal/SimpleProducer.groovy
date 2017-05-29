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
    public void publishToBroker(Object m) throws UnpublishableException {
        try {
            super.publish(m);
        } catch (IllegalStateException e) {
            log.error("error occured while publishing the message.Unless handled by the client,this message is lost for ever", e);
            throw new UnpublishableException("This message could not be published even after retrying")
        }
    }
}
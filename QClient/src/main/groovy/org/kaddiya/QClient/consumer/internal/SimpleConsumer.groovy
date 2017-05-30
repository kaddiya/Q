package org.kaddiya.QClient.consumer.internal

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.AbstractBrokerAdapter
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.consumer.models.Consumer


@CompileStatic
@Slf4j
class SimpleConsumer<T> extends BaseConsumer implements Consumer<T> {

    public SimpleConsumer(String topicId, BrokerConfig cfg) {
        super(topicId, cfg)
    }

    @Override
    void registerSubscription() {

    }

    @Override
    Message consumeMessage() {
        return null
    }

    @Override
    void registerAckFor(Message m) {

    }
}

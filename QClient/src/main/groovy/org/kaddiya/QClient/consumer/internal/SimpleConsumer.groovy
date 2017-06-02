package org.kaddiya.QClient.consumer.internal

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.BrokerConfig

@CompileStatic
@Slf4j
class SimpleConsumer<T> extends BaseConsumer<T> implements Consumer {


    public SimpleConsumer(String consumerId, String topicId, BrokerConfig cfg, List<String> consumerDependencies, Closure<T> marshallingCallback, Class<T> contentClass) {
        super(consumerId, topicId, cfg, consumerDependencies, 1, marshallingCallback, contentClass)

    }

    @Override
    public void consumeMesage() {
        longPollForMessageAndActOnMessage();
    }

}


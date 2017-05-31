package org.kaddiya.QClient.consumer.internal

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.BrokerConfig

@CompileStatic
@Slf4j
class SimpleConsumer<T> extends BaseConsumer {

    public SimpleConsumer(String topicId, BrokerConfig cfg, List<String> consumerDependencies) {
        super(topicId, cfg, consumerDependencies)

    }
}


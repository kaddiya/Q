package org.kaddiya.QClient.consumer.internal

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.Request
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.Message

import java.util.concurrent.Callable
import java.util.concurrent.Future

@CompileStatic
@Slf4j
class SimpleConsumer<T> extends BaseConsumer  {

    public SimpleConsumer(String topicId, BrokerConfig cfg, List<String> consumerDependencies) {
        super(topicId, cfg, consumerDependencies,1)

    }

    public Message waitForMessage(){
       Future<Message> messageFuture = this.messagePollerExecutor.submit(poller)
        return messageFuture.get()
    }


}


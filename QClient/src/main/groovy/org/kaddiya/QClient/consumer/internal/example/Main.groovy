package org.kaddiya.QClient.consumer.internal.example

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.consumer.internal.SimpleConsumer

@CompileStatic
@Slf4j
class Main {

    public static void main(String[] arggs) {

        SimpleConsumer bc = new SimpleConsumer<SomePayload>("1", new BrokerConfig("http", "localhost", 8080),Arrays.asList(""))
    }


}


package org.kaddiya.QClient.consumer.internal.example

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.consumer.internal.SimpleConsumer

@CompileStatic
@Slf4j
class Main {

    public static void main(String[] arggs) {
        Closure<SomePayload> parsingClosure = { Message m ->
            SomePayload p = new SomePayload("a");
            return p

        }
        SimpleConsumer bc = new SimpleConsumer<SomePayload>("1", new BrokerConfig("http", "localhost", 8080), Arrays.asList(""), parsingClosure)
        bc.consumeMesage();

    }


}


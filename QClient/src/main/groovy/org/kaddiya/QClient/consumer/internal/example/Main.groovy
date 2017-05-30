package org.kaddiya.QClient.consumer.internal.example

import com.sun.org.apache.xpath.internal.operations.Bool
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.consumer.internal.SimpleConsumer

import java.util.function.Function

@CompileStatic
@Slf4j
class Main {

    public static void main(String[] arggs) {
//        Function<Message,?> x

        SimpleConsumer bc = new SimpleConsumer<SomePayload>("1", new BrokerConfig("http", "localhost", 8080))
    }

    void someMethod(Message m, Boolean b) {

    }
}


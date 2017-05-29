package org.kaddiya.QClient.example

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.models.producer.internal.SimpleProducer
import org.kaddiya.QClient.models.producer.models.BrokerConfig

@CompileStatic
@Slf4j
public class Main {

    public static void main(String[] args) {
        String protocol = "HTTP";
        String brokerHost = System.getenv("BROKER_HOST");
        String brokerPort = System.getenv("BROKER_PORT");
        BrokerConfig bc = new BrokerConfig(protocol, brokerHost, Integer.valueOf(brokerPort));
        SimpleProducer s = new SimpleProducer("2", bc);


        SampleMessage sm = new SampleMessage();
        sm.setName("s");
        sm.setValid(true);
        sm.setAge(12);
        s.publishToBroker(sm);


    }
}

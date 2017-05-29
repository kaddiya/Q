package org.kaddiya.QClient.example

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.producer.internal.SimpleProducer
import org.kaddiya.QClient.producer.models.UnpublishableException

@CompileStatic
@Slf4j
public class Main {

    public static void main(String[] args) {

        String protocol = "HTTP";
        String brokerHost = System.getenv("BROKER_HOST");
        String brokerPort = System.getenv("BROKER_PORT");
        BrokerConfig bc = new BrokerConfig(protocol, brokerHost, Integer.valueOf(brokerPort));
        SimpleProducer s = new SimpleProducer("1", bc);


        int i = 0
        while (true) {
            try {
                i++
                SampleMessage sm = new SampleMessage();
                sm.setName("s" + i);
                sm.setValid(true);
                sm.setAge(i);
                s.publishToBroker(sm);
            } catch (UnpublishableException e) {
                log.error("Error occured while publishing the message", e)
            }
            Thread.sleep(5000)
        }
    }
}

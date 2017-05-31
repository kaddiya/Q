package org.kaddiya.QClient.producer.internal

import com.google.gson.Gson
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.Request
import okhttp3.Response
import org.kaddiya.QClient.common.AbstractBrokerAdapter
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.BrokerException
import org.kaddiya.QClient.producer.models.Producer
import org.kaddiya.QClient.producer.models.PublishRequest
import org.kaddiya.QClient.producer.models.UnpublishableException

@Slf4j
@CompileStatic
public class SimpleProducer extends AbstractBrokerAdapter implements Producer {

    private String PRODUCER_URL = "producer"

    public SimpleProducer(String topicId, BrokerConfig bCfg) {
        super(bCfg, topicId,3);
    }

    @Override
    public void publishToBroker(Object m) throws UnpublishableException {
        try {
            String payload = new Gson().toJson(m)
            PublishRequest request = new PublishRequest(payload, topicId)
            Request httpRequest = constructPostRequest(request, PRODUCER_URL)
            interactWithBrokerOverNetworkWithRetries(httpRequest)
        } catch (IllegalStateException e) {
            log.error("error occured while publishing the message.Unless handled by the client,this message is lost for ever", e);
            throw new UnpublishableException("This message could not be published even after retrying")
        }
    }

    @Override
    protected Object handleResponseFromBroker(Response res) {
        res.withCloseable {
            log.info("HTTP code received is {}", String.valueOf(res.code()))
            switch (res.code()) {
                case 507:
                    //thrown when the queue is full
                    throw new BrokerException("The broker is operating at the highest capacity")
                    break;
                case 200:
                    break;
                default:
                    log.error("Invalid error code encountered", String.valueOf(res.code()))
            }

        }
        return true
    }
}
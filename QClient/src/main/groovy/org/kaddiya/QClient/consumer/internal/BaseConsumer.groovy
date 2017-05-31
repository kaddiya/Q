package org.kaddiya.QClient.consumer.internal

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.Request
import okhttp3.Response
import org.kaddiya.QClient.common.AbstractBrokerAdapter
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.consumer.models.ConsumptionException
import org.kaddiya.QClient.consumer.models.RegistrationException
import org.kaddiya.QClient.consumer.models.SubscriptionRegistrationRequest

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@CompileStatic
@Slf4j
public class BaseConsumer<T> extends AbstractBrokerAdapter {


    private final int MAX_RETRY_LIMIT = 3; //lets work with 3 retries
    private final String consumerId
    private final String SUBSCRIPTION_CONFIRMATION_URL = "consumer_registration"
    ExecutorService executor = Executors.newFixedThreadPool(1);
    private List<String> depdenciesOfConsumers

    public BaseConsumer(String topicId, BrokerConfig cfg, List<String> depdenciesOfConsumers) {
        super(cfg, topicId)
        this.consumerId = UUID.randomUUID().toString();
        this.depdenciesOfConsumers = depdenciesOfConsumers
        //set off by having to register a subscription request
        SubscriptionRegistrationRequest request = new SubscriptionRegistrationRequest(topicId, consumerId, depdenciesOfConsumers)
        Request httpReq = super.constructPostRequest(request, SUBSCRIPTION_CONFIRMATION_URL);
        interactWithBrokerOverNetworkWithRetries(httpReq);


        while (true) {
            Request consumptionRequest = constructGetRequest("consumer/" + topicId);
            Object o = interactWithBrokerOverNetworkWithRetries(consumptionRequest);
            Message m = o as Message
            if (m != null) {
                log.info(m.content)
            }
        }
    }

    @Override
    protected Object handleResponseFromBroker(Response res) {
        res.withCloseable {

            switch (res.code()) {
                case 409:
                    throw new RegistrationException("Could not register the consumer with id" + this.consumerId);
                    break
                case 500:
                    throw new IllegalStateException("error encountered")
                case 404:
                    throw new ConsumptionException("No message yet available")
                case 200:
                    break;
                default:
                    log.info(String.valueOf(res.code()))
            }

        }
    }

    class MessagePoller implements Callable<Message> {

        String topicId

        public MessagePoller(String topicId) {
            this.topicId = topicId
        }

        @Override
        Message call() throws Exception {
            return null
        }
    }
}

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

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@CompileStatic
@Slf4j
public class BaseConsumer<T> extends AbstractBrokerAdapter {


    private final String consumerId
    private final String SUBSCRIPTION_CONFIRMATION_URL = "consumer_registration"

    private List<String> depdenciesOfConsumers
    protected ExecutorService messagePollerExecutor = Executors.newFixedThreadPool(1);

    public BaseConsumer(String topicId, BrokerConfig cfg, List<String> depdenciesOfConsumers, Integer retries) {
        super(cfg, topicId, retries)
        this.consumerId = UUID.randomUUID().toString();
        this.depdenciesOfConsumers = depdenciesOfConsumers

        //set off by having to register a subscription request
        SubscriptionRegistrationRequest request = new SubscriptionRegistrationRequest(topicId, consumerId, depdenciesOfConsumers)
        Request httpReq = super.constructPostRequest(request, SUBSCRIPTION_CONFIRMATION_URL);

        try {
            interactWithBrokerOverNetworkWithRetries(httpReq);
        } catch (IOException e) {
            log.warn("There was an issue with the connection.System will silently wait for the Broker to respond")
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
                    // since we know that in the consumer we are going to get in a string format return the response body as string when there is a 200
                    return res.body().string()
                    break;
                default:
                    log.info(String.valueOf(res.code()))
            }

        }
    }


    protected void pollAndWaitForMessage() {
        while (true) {
            Request request = constructGetRequest("consumer/" + topicId)
            try {
                Object o = interactWithBrokerOverNetworkWithRetries(request);
                log.info(o as String)
                Message m = gson.fromJson(o as String, Message);
                log.info(m.content)
            } catch (IllegalStateException e) {
                log.error("Could not get a message even after some retrying")
            } catch (IOException ioe) {
                log.warn("There was an connection issue encountered.The system is not going down but silently waiting for the broker to respond ")
            }

        }
    }


}

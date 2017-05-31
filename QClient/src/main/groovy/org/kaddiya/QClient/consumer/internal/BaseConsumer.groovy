package org.kaddiya.QClient.consumer.internal

import com.google.gson.Gson
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.*
import org.kaddiya.QClient.common.AbstractBrokerAdapter
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.consumer.models.RegistrationException
import org.kaddiya.QClient.consumer.models.SubscriptionRegistrationRequest

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

@CompileStatic
@Slf4j
public class BaseConsumer extends AbstractBrokerAdapter {


    private final int MAX_RETRY_LIMIT = 3; //lets work with 3 retries
    private final String consumerId
    private final String SUBSCRIPTION_CONFIRMATION_URL = "consumer_registration"
    ExecutorService executor = Executors.newFixedThreadPool(1);

    public BaseConsumer(String topicId, BrokerConfig cfg) {
        super(cfg, topicId)
        this.consumerId = UUID.randomUUID().toString();

        //set off by having to register a subscription request
        /*SubscriptionRegistrationRequest request = new SubscriptionRegistrationRequest(topicId, consumerId)
        Request req = super.constructPostRequest(request, SUBSCRIPTION_CONFIRMATION_URL);
        Response res = doNetWorkStuffWithRetries(req)
        res.withCloseable {
            log.info(String.valueOf(res.code()))
            if (res.code() != 200) {
                throw new RegistrationException("Could not register the consumer with id");
            }
        }*/

    }

    @Override
    protected void handleResponseFromBroker(Response r) {

    }
}

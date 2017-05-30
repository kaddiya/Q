package org.kaddiya.QClient.consumer.internal

import com.google.gson.Gson
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.*
import org.kaddiya.QClient.common.AbstractBrokerAdapter
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.consumer.models.SubscriptionRegistrationRequest

@CompileStatic
@Slf4j
public class BaseConsumer extends AbstractBrokerAdapter {


    private final int MAX_RETRY_LIMIT = 3; //lets work with 3 retries
    private final String consumerId
    private final String SUBSCRIPTION_CONFIRMATION_URL = "consumer_registration"

    public BaseConsumer(String topicId, BrokerConfig cfg) {
        super(cfg,topicId)
        this.consumerId = UUID.randomUUID().toString();

        //set off by having to register a subscription request
        SubscriptionRegistrationRequest request = new SubscriptionRegistrationRequest(topicId, consumerId)
        Request req = super.constructPostRequest(request, SUBSCRIPTION_CONFIRMATION_URL);
        makeHttpCall(req)
    }



}

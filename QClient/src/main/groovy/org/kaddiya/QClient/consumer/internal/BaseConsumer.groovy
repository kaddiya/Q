package org.kaddiya.QClient.consumer.internal

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.Request
import okhttp3.Response
import org.kaddiya.QClient.common.AbstractBrokerAdapter
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.consumer.models.AckRequest
import org.kaddiya.QClient.consumer.models.ConsumptionException
import org.kaddiya.QClient.consumer.models.RegistrationException
import org.kaddiya.QClient.consumer.models.SubscriptionRegistrationRequest

@CompileStatic
@Slf4j
public class BaseConsumer<T> extends AbstractBrokerAdapter {


    private final String consumerId
    private final String SUBSCRIPTION_CONFIRMATION_URL = "consumer_registration"
    protected final Closure<T> callback
    private List<String> depdenciesOfConsumers
    protected final Class<T> contentClass

    public BaseConsumer(String consumerId, String topicId, BrokerConfig cfg, List<String> depdenciesOfConsumers, Integer retries, Closure<T> callback, Class<T> contentClass) {
        super(cfg, topicId, retries)
        this.consumerId = consumerId
        this.depdenciesOfConsumers = depdenciesOfConsumers
        this.callback = callback
        this.contentClass = contentClass

        //set off by having to register a subscription request
        SubscriptionRegistrationRequest request = new SubscriptionRegistrationRequest(topicId, consumerId, depdenciesOfConsumers)
        Request httpReq = super.constructPostRequest(request, SUBSCRIPTION_CONFIRMATION_URL);

        try {
            interactWithBrokerOverNetworkWithRetries(httpReq);
        } catch (IOException e) {
            log.warn(CONNECTION_ERROR_MESSAGE, e.getMessage())
            throw new IllegalStateException("The consumer cant start if the broker is not up")
        }


    }

    @Override
    protected Object handleResponseFromBroker(Response res) {
        res.withCloseable {
            switch (res.code()) {
                case 409:
                    throw new IllegalArgumentException("A consumer with " + this.consumerId + " has already been registered");
                    break
                case 500:
                    throw new IllegalStateException("unknown error encountered")
                case 404:
                    throw new ConsumptionException("No message yet available")
                case 301:
                    log.info("The ack has been sucessfully registered")
                    break
                case 403:
                    throw new RegistrationException("Could not find the registration for this consumer Id.Please re-register.The only way to re-register now is to restart")
                case 200:
                    // since we know that in the consumer we are going to get in a string format return the response body as string when there is a 200
                    return res.body().string()
                    break;
                default:
                    log.info("Unhandled Code Encountered : " + String.valueOf(res.code()))
            }

        }
    }

    private T marshallObjectFromMesage(String jsonContent) {
        return new Gson().fromJson(jsonContent, this.contentClass);
    }


    protected void longPollForMessageAndActOnMessage() {
        while (true) {
            Request request = constructGetRequest("consumer/" + consumerId + "/" + topicId)
            try {
                Object o = interactWithBrokerOverNetworkWithRetries(request);
                if (o != null) {

                    try {
                        Message m = gson.fromJson(o as String, Message);
                        //if a message is recieved,
                        T result = marshallObjectFromMesage(m.content)
                        if (result) {
                            AckRequest ack = new AckRequest(m.uuid);
                            Request ackRequest = constructPostRequest(ack, "consumer/" + consumerId + "/" + topicId)
                            interactWithBrokerOverNetworkWithRetries(ackRequest);
                            callback.call(result);
                        }

                    } catch (JsonSyntaxException e) {
                        log.warn("Cant recognise the message type")
                    }


                }
            } catch (IllegalStateException e) {
                log.error("could not interact with the broker even after trying", e)
            } catch (IOException ioe) {
                log.warn(CONNECTION_ERROR_MESSAGE, ioe.getMessage())
            }
            Thread.sleep(1000)
        }
    }


}

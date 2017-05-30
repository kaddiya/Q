package org.kaddiya.QClient.consumer.internal

import com.google.gson.Gson
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.*
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.consumer.models.SubscriptionRegistrationRequest

@CompileStatic
@Slf4j
public class BaseConsumer {

    private final OkHttpClient httpClient;
    protected final Gson gson = new Gson();
    private final String topicId;
    protected final BrokerConfig bCfg;
    private final int MAX_RETRY_LIMIT = 3; //lets work with 3 retries
    private final String consumerId
    private final String SUBSCRIPTION_CONFIRMATION_URL = "consumer"

    protected BaseConsumer(String topicId, BrokerConfig cfg) {
        this.bCfg = cfg;
        this.topicId = topicId;
        this.httpClient = new OkHttpClient.Builder().build();
        this.consumerId = UUID.randomUUID().toString();
        //set off by having to register a subscription request
        SubscriptionRegistrationRequest request = new SubscriptionRegistrationRequest(topicId, consumerId)
        Response res = this.httpClient.newCall(constructRequest(request, SUBSCRIPTION_CONFIRMATION_URL)).execute();
        res.withCloseable { it ->
            if ((it as Response).code() != 200) {
                throw new IllegalStateException("Failed to register the subscription request")
            }
        }

    }


    private Request constructRequest(Object content, String urlPath) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String payload = gson.toJson(content)

        return new Request.Builder()
                .url(getCanonicalURL(urlPath))
                .post(RequestBody.create(JSON, this.gson.toJson(payload)))
                .build();

    }

    private String getCanonicalURL(String urlPath) {

        return new HttpUrl.Builder()
                .scheme(this.bCfg.getProtocol())
                .host(this.bCfg.getHost())
                .port(this.bCfg.getPort())
                .addPathSegment(urlPath).build()

    }
}

package org.kaddiya.QClient.common;

import com.google.gson.Gson
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j;
import okhttp3.*;

import java.io.IOException;

@CompileStatic
@Slf4j
public abstract class AbstractBrokerAdapter {
    protected final BrokerConfig brokerConfig;
    protected final String topicId;
    protected final OkHttpClient httpClient;
    protected final Gson gson = new Gson();
    private final Integer MAX_RETRY_LIMIT = 3

    public AbstractBrokerAdapter(BrokerConfig bc, String topicId) {
        this.brokerConfig = bc;
        this.topicId = topicId;
        this.httpClient = new OkHttpClient.Builder().build();
    }


    protected String getCanonicalURL(String urlPath) {

        return new HttpUrl.Builder()
                .scheme(this.brokerConfig.getProtocol())
                .host(this.brokerConfig.getHost())
                .port(this.brokerConfig.getPort())
                .addPathSegment(urlPath).build().toString();

    }

    protected Request constructPostRequest(Object content, String urlPath) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url(getCanonicalURL(urlPath))
                .post(RequestBody.create(JSON, this.gson.toJson(content)))
                .build();

    }

    protected abstract boolean handleResponseFromBroker(Response r)

    private Boolean makeHttpCall(Request request) {
        Call c = this.httpClient.newCall(request);
        try {
            Response res = c.execute()
            return  handleResponseFromBroker(res)
        } catch (Exception e) {
            throw new IllegalStateException("Could not execute the API call",e.getCause())
        }

    }

    protected Request constructGetRequest(String urlPath) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url(getCanonicalURL(urlPath))
                .get()
                .build();

    }


    protected Boolean interactWithBrokerOverNetworkWithRetries(Request request) {
        int iterationCount = 0;
        while (iterationCount <= MAX_RETRY_LIMIT) {
            iterationCount++
            try {
               return makeHttpCall(request)

            } catch (IOException | BrokerException |IllegalStateException e) {
                log.error("Could not publish the message due to ", e)
                try {
                    //lets sleep for a while and see if world will be backl to normal when we get up!
                    Thread.sleep(((int) Math.round(Math.pow(2, iterationCount)) * 1000));
                } catch (InterruptedException ignored) {
                    //doesnt matter
                }

                if (iterationCount == MAX_RETRY_LIMIT) {
                    throw new IllegalStateException("You have exhausted the retries for trying to publish this message")
                }

            }
        }
        return true

    }
}
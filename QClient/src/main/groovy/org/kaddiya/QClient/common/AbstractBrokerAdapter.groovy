package org.kaddiya.QClient.common

import com.google.gson.Gson
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.*

@CompileStatic
@Slf4j
public abstract class AbstractBrokerAdapter {
    protected final BrokerConfig brokerConfig;
    protected final String topicId;
    protected final OkHttpClient httpClient;
    protected final Gson gson = new Gson();
    private final Integer MAX_RETRY_LIMIT

    protected
    final String CONNECTION_ERROR_MESSAGE = "There was an connection issue encountered.The system is not going down but silently waiting for the broker to respond "

    public AbstractBrokerAdapter(BrokerConfig bc, String topicId, Integer retries) {
        this.brokerConfig = bc;
        this.topicId = topicId;
        this.MAX_RETRY_LIMIT = retries
        this.httpClient = new OkHttpClient.Builder().build();
    }


    protected String getCanonicalURL(String urlPath) {

        return getDecodedUrl(new HttpUrl.Builder()
                .scheme(this.brokerConfig.getProtocol())
                .host(this.brokerConfig.getHost())
                .port(this.brokerConfig.getPort())
                .addPathSegment(urlPath).build())

    }

    protected Request constructPostRequest(Object content, String urlPath) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url(getCanonicalURL(urlPath))
                .post(RequestBody.create(JSON, this.gson.toJson(content)))
                .build();

    }

    protected abstract Object handleResponseFromBroker(Response r)

    private Response makeHttpCall(Request request) {
        Call c = this.httpClient.newCall(request);
        Response res = c.execute()
        return res
    }

    protected Request constructGetRequest(String urlPath) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url(getCanonicalURL(urlPath))
                .get()
                .build();

    }

    private String getDecodedUrl(HttpUrl encodedUrl) {
        return java.net.URLDecoder.decode(encodedUrl.toString(), "UTF-8")
    }

    protected Object interactWithBrokerOverNetworkWithRetries(Request request) throws IOException {
        int iterationCount = 1;
        while (iterationCount <= MAX_RETRY_LIMIT) {

            try {
                Response r = makeHttpCall(request)
                return handleResponseFromBroker(r)
            }
            catch (RetryableException e) {
                iterationCount++
                log.debug("Could not get response from broker due to " + e.getMessage())
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

    }
}
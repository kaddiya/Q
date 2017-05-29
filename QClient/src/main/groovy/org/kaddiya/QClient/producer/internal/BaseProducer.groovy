package org.kaddiya.QClient.producer.internal

import com.google.gson.Gson
import groovy.util.logging.Slf4j
import okhttp3.*
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.BrokerException
import org.kaddiya.QClient.producer.models.PublishRequest
import org.kaddiya.QClient.producer.models.UnpublishableException

@Slf4j
public class BaseProducer<T> {

    private final OkHttpClient httpClient
    protected final Gson gson = new Gson()
    private final String topicId
    protected final BrokerConfig bCfg
    private final int MAX_RETRY_LIMIT = 3 //lets work with 3 retries

    public BaseProducer(String topicId, BrokerConfig cfg) {
        this.bCfg = cfg
        this.topicId = topicId
        this.httpClient = new OkHttpClient.Builder().build()

    }


    private Request constructRequest(T content) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String payload = gson.toJson(content)
        PublishRequest request = new PublishRequest(payload, topicId)
        return new Request.Builder()
                .url(getCanonicalURL())
                .post(RequestBody.create(JSON, this.gson.toJson(request)))
                .build();
    }

    private String getCanonicalURL() {

        return new HttpUrl.Builder()
                .scheme(this.bCfg.protocol)
                .host(this.bCfg.host)
                .port(this.bCfg.port)
                .addPathSegment("/producer").build().toString()

    }

    private String getDecodedUrl(HttpUrl encodedUrl) {
        return java.net.URLDecoder.decode(encodedUrl.toString(), "UTF-8")
    }


    protected boolean publish(T message) {
        int iterationCount = 0;
        while (iterationCount <= MAX_RETRY_LIMIT) {
            iterationCount++
            try {
                doWork(message)
                return true
            } catch (IOException | BrokerException e) {
                log.warn("Current iteration is :", iterationCount)
                log.warn("Could not publish the message.Error is ", e)

                try {
                    //lets sleep for a while and see if world will be backl to normal when we get up!
                    Thread.sleep(((int) Math.round(Math.pow(2, iterationCount)) * 1000));
                } catch (InterruptedException ignored) {
                    //doesnt matter
                }

                if (iterationCount == MAX_RETRY_LIMIT) {
                    throw new UnpublishableException("You have exhausted the retries for trying to publish the message")
                }

            }
        }
        //if everything happens just fine we are home free

    }

    private void doWork(T message) {
        this.httpClient.newCall(constructRequest(message)).execute().withCloseable { Response res ->
            switch (res.code()) {
                case 507:
                    //thrown when the queue is full
                    log.error("The broker is operating at the highest capacity")
                    throw new BrokerException(res.code(), "The broker is operating at the highest capacity")
                    break;
                case 200:
                    log.info("Sucessfully published the message")
                    break;
                 default:
                     System.out.print(res.code())


            }
        }
    }
}

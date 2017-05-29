package org.kaddiya.QClient.models.producer.internal

import com.google.gson.Gson
import okhttp3.*
import org.kaddiya.QClient.models.producer.models.BrokerConfig
import org.kaddiya.QClient.models.producer.models.BrokerException
import org.kaddiya.QClient.models.producer.models.PublishRequest

public class BaseProducer<T> {

    private final OkHttpClient httpClient
    protected final Gson gson = new Gson()
    private final String topicId
    protected final BrokerConfig bCfg

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


    protected void publish(T message) {
        doWork(message)
    }

    private void doWork(T message) {
        this.httpClient.newCall(constructRequest(message)).execute().withCloseable { Response res ->
            if (res.code() != 200) {
                throw new BrokerException(res.code(), "The broker is up but is refusing to accept any publications now")
            }
        }
    }
}

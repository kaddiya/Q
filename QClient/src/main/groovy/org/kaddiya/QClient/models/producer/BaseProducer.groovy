package org.kaddiya.QClient.models.producer

import com.google.gson.Gson
import groovy.json.JsonOutput
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

/**
 * Created by Webonise on 28/05/17.
 */
public  class BaseProducer<T> {

    private  final OkHttpClient httpClient
    private final Gson gson = new Gson()

    protected BaseProducer() {

        this.httpClient = new OkHttpClient.Builder().build()

    }


    private Request constructRequest(T content) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        return new Request.Builder()
                .url(getCanonicalURL())
                .post(RequestBody.create(JSON, gson.toJson(content)))
                .build();
    }

    private String getCanonicalURL() {

        return getDecodedUrl(new HttpUrl.Builder()
                .scheme("http")
                .host("localhost")
                .port("8080" as int)
                .addPathSegment("/producer").build()
        )
    }

    private String getDecodedUrl(HttpUrl encodedUrl) {
        return java.net.URLDecoder.decode(encodedUrl.toString(), "UTF-8")
    }


    protected void publish(T message){
        this.httpClient.newCall(constructRequest(message)).execute()
    }

}

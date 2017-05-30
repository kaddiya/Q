package org.kaddiya.QClient.common;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;


public abstract class AbstractBrokerAdapter {
    protected final BrokerConfig brokerConfig;
    protected final String topicId;
    protected final OkHttpClient httpClient;
    protected final Gson gson = new Gson();

    public AbstractBrokerAdapter(BrokerConfig bc,String topicId){
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

    protected ResponseBody makeHttpCall(Request request){
        Call c = this.httpClient.newCall(request);
        Response res = null;
        try {
            res = c.execute();
        } catch (IOException e) {
            throw new IllegalStateException("Error occured while executing the API call");
        }finally {
            res.close();
        }

        return res !=null ? res.body() : null;
    }

}

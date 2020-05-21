package com.deepexi.activity.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 */
public class HttpPostClient {

    public static String getReq(String reqUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(reqUrl)
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "68def97c-4c25-a499-3754-c41529534984")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String getReq(String reqUrl, Map<String, String> param) throws IOException {
        OkHttpClient client = new OkHttpClient();

        StringBuffer sb = new StringBuffer("?");
        for (Map.Entry entry : param.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        Request request = new Request.Builder()
                .url(reqUrl + sb.toString())
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "68def97c-4c25-a499-3754-c41529534984")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static String postMap(String url, Map<String, String> paramMap) throws IOException {
        return postJson(url, JSON.toJSONString(paramMap));
    }

    private static Logger logger = LoggerFactory.getLogger(HttpPostClient.class.getName());

    public static String postJson(String url, String jsonStr) throws IOException {
        logger.info(url);
        logger.info("params:" + jsonStr);
        System.out.println(jsonStr);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

}

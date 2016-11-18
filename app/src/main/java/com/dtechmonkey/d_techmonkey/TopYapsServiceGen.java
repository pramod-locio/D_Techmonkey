package com.dtechmonkey.d_techmonkey;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class TopYapsServiceGen {

    private static boolean isInterceptorAdded = false;

    private static final String base_URL = "http://topyaps.com/";

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(base_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <T> T createService(Class<T> serviceClass) {
        addLoggingInterceptor(httpClient);
        Retrofit retrofit = retrofitBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    private static void addLoggingInterceptor(OkHttpClient.Builder builder) {
        if (!isInterceptorAdded) {
            isInterceptorAdded = true;
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
    }
}
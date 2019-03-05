package com.example.crazynet.bakingapp.webService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by CrazyNet on 16/08/2018.
 */
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitWebService {
    private static final String TAG = RetrofitWebService.class.getSimpleName();
    private static final Map<String, RetrofitService> mServices = new HashMap<>();
    private static final String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/";

    private RetrofitWebService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mServices.put(url, retrofit.create(RetrofitService.class));
    }

    public static RetrofitService getService() {
        if (null == mServices.get(url)) {
            new RetrofitWebService();
        }
        return mServices.get(url);
    }


}

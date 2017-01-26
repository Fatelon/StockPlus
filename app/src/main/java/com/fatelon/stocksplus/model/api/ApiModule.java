package com.fatelon.stocksplus.model.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import static com.fatelon.stocksplus.Constants.BASE_URL;

/**
 * Created by Fatelon on 20.01.2017.
 */

public class ApiModule {

    private static ApiInterface mApiInterface = null;

    public static ApiInterface getApiInterface() {
        if (mApiInterface == null) {
            Gson gson = new GsonBuilder().create();
//            OkHttpClient httpClient = new OkHttpClient();
//            CookieHandler cookieHandler = new CookieManager();

            RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "Basic auth"); // <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();

            Retrofit.Builder builder = new Retrofit.Builder().
                    baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
//                    .addCallAdapterFactory(rxAdapter);
//                .client(client);

            builder.client(client);


            mApiInterface = builder.build().create(ApiInterface.class);
//            ApiInterface apiInterface = builder.build().create(ApiInterface.class);

        }


//        Gson gson = new GsonBuilder().create();
//
//        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
//
//        Retrofit.Builder builder = new Retrofit.Builder().
//                baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(rxAdapter);
//
//
//        ApiInterface apiInterface = builder.build().create(ApiInterface.class);

        return mApiInterface;
    }

}

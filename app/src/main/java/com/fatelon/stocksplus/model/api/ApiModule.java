package com.fatelon.stocksplus.model.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import static com.fatelon.stocksplus.Constants.BASE_URL;

/**
 * Created by User on 20.01.2017.
 */

public class ApiModule {

    public static ApiInterface getApiInterface() {

        Gson gson = new GsonBuilder().create();

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter);


        ApiInterface apiInterface = builder.build().create(ApiInterface.class);

        return apiInterface;
    }
}

package com.fatelon.stocksplus.presenter;

import android.util.Log;
import android.view.View;

import com.fatelon.stocksplus.model.Model;
import com.fatelon.stocksplus.model.ModelImpl;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.dto.LoginDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import static com.fatelon.stocksplus.Constants.BASE_URL;

/**
 * Created by User on 20.01.2017.
 */

public class LoginPresenter {

    private static String TAG = LoginPresenter.class.getSimpleName();

    private Model model = new ModelImpl();

    private View view;

    private Subscription subscription = Subscriptions.empty();

    public LoginPresenter(View view) {
        this.view = view;
    }

    public void onLoginClick() {

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        Map<String, String> m = new HashMap<>();
        m.put("username", "462");
        m.put("password", "65464");

//        ApiInterface ai = ApiModule.getApiInterface();

//        Observable<LoginDTO> obs = ai.postLogin(m);

        Log.d(TAG, "init");

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();

        ApiInterface apiService = retrofit.create(ApiInterface.class);

        Observable<LoginDTO> obs = apiService.postLogin(m);

        subscription = obs.subscribe(new Subscriber<LoginDTO>() {
            @Override
            public void onCompleted() {
                //do nothing
                Log.d(TAG, "onCompleted");
        }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError - " + e.toString());
            }

            @Override
            public void onNext(LoginDTO models) {
                Log.d(TAG, "onNext - " + models.getSessionId());
            }
        });

//        subscription = model.
    }
}

package com.fatelon.stocksplus.presenter;

import android.util.Log;
import android.view.View;

import com.fatelon.stocksplus.model.Model;
import com.fatelon.stocksplus.model.ModelImpl;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.LoginDTO;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

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
        m.put("username", "665");
        m.put("password", "2");

        ApiInterface ai = ApiModule.getApiInterface();

        Observable<LoginDTO> obs = ai.postLogin(m);

        Log.d(TAG, "init");


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
                Log.d(TAG, "onNext - ");
            }
        });

//        subscription = model.
    }
}

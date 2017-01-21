package com.fatelon.stocksplus.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.ErrorHelper;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.LoginDTO;
import com.fatelon.stocksplus.view.LoadingCallBack;
import com.fatelon.stocksplus.view.MenuActivity;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.fatelon.stocksplus.Constants.SERVER_MESSAGE_ERROR;

/**
 * Created by User on 19.01.2017.
 */

public class LoginScreen extends BaseFragment {

    private static String TAG = LoginScreen.class.getSimpleName();


    private EditText userName;

    private EditText userPass;

    private Button loginButton;

    private LoadingCallBack loadingCallBack;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        try {
            loadingCallBack = (LoadingCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement activityCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        init(view);

        loginButton.setOnClickListener(v -> onClickLoginButton(v));


        return view;
    }

    private void init(View v) {
        userName = (EditText) v.findViewById(R.id.login_user_name);
        userPass = (EditText) v.findViewById(R.id.login_password);
        loginButton = (Button) v.findViewById(R.id.login_button);

    }

    public void onClickLoginButton(View v){
        String uName = userName.getText().toString();
        String uPass = userPass.getText().toString();
        if (uName.isEmpty() || uPass.isEmpty()) {
            ErrorHelper.uNameOrPassIsEmpty(context);
        } else {
            showLoading();
            Map<String, String> m = new HashMap<>();
            m.put(context.getResources().getString(R.string.user_name), uName);
            m.put(context.getResources().getString(R.string.user_password), uPass);
            ApiInterface apiInterface = ApiModule.getApiInterface();
//            Observable<LoginDTO> obs = ai.postLogin(m);
//            Subscription subscription =
            apiInterface.postLogin(m).subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Subscriber<LoginDTO>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            hideLoading();
                            ErrorHelper.failedToConnectSimpleDialog(context, e);
                        }

                        @Override
                        public void onNext(LoginDTO user) {
                            Log.d(TAG, "onNext - ");
                            hideLoading();
                            try {
                                int error = user.getError();
                                if (error == SERVER_MESSAGE_ERROR) {
                                    ErrorHelper.authError(context);
                                } else {

                                    Intent intent = new Intent(context, MenuActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    ((Activity) context).finish();
//                                    startActivity(new Intent(context, MenuActivity.class));
//                                    SimpleDialog.showSimpleDialog(context, "Good", "Life is good");
                                }
//                    SimpleDialog.showSimpleDialog(context, "user", "sessionId - " + user.getSessionId());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
//                SimpleDialog.showSimpleDialog(context, "error", "sessionId - " + user.getSessionId());
                        }
                    });
        }
    }

    public void hideLoading() {
        loadingCallBack.hideLoading();
    }

    private void showLoading() {
        loadingCallBack.showLoading();
    }

}

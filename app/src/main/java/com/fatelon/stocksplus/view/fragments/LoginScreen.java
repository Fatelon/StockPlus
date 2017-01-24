package com.fatelon.stocksplus.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.ErrorHelper;
import com.fatelon.stocksplus.helpers.PreferencesHelper;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.LoginDTO;
import com.fatelon.stocksplus.view.callbacks.LoadingCallBack;
import com.fatelon.stocksplus.view.callbacks.UserActionsCallBack;
import com.fatelon.stocksplus.view.customviews.CustomTextView;

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

    private CustomTextView forgotPasswordButton;
    private CustomTextView registerationButton;

    private EditText userName;

    private EditText userPass;

    private Button loginButton;

    private CheckBox saveMeCheckBox;

    private LoadingCallBack loadingCallBack;

    private UserActionsCallBack userActionsCallBack;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        try {
            loadingCallBack = (LoadingCallBack) context;
            userActionsCallBack = (UserActionsCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement activityCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);

        if (PreferencesHelper.getIsUserLogin(context)) {
            checkLogin(PreferencesHelper.getUserName(context), PreferencesHelper.getUserPass(context));
        }

        return view;
    }

    private void init(View view) {
        userName = (EditText) view.findViewById(R.id.login_user_name);
        userPass = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> onClickLoginButton(v));
        forgotPasswordButton = (CustomTextView) view.findViewById(R.id.forgot_password_button);
        registerationButton = (CustomTextView) view.findViewById(R.id.new_user_button);
        registerationButton.setOnClickListener(v -> onClickRegistrationButton(v));
        saveMeCheckBox = (CheckBox) view.findViewById(R.id.login_save_me_check_box);
    }

    private void onClickRegistrationButton(View view) {
        if (userActionsCallBack != null) userActionsCallBack.registrationAction();
    }

    private void onClickLoginButton(View view){
        String uName = userName.getText().toString();
        String uPass = userPass.getText().toString();
        checkLogin(uName, uPass);
    }

    private void checkLogin(String uName, String uPass) {
        if (uName.isEmpty() || uPass.isEmpty()) {
            ErrorHelper.uNameOrPassIsEmpty(context);
        } else {
            if (loadingCallBack != null) loadingCallBack.showLoading();
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
                            if (loadingCallBack != null) loadingCallBack.hideLoading();
                            ErrorHelper.failedToConnectSimpleDialog(context, e);
                        }

                        @Override
                        public void onNext(LoginDTO user) {
                            Log.d(TAG, "onNext - ");
                            if (loadingCallBack != null) loadingCallBack.hideLoading();
                            try {
                                int error = user.getError();
                                if (error == SERVER_MESSAGE_ERROR) {
                                    ErrorHelper.authError(context);
                                } else {
                                    doLogin(user);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

        }
    }

    private void doLogin(LoginDTO user) {
        try {
            String sessionId = user.getSessionId();
            if (saveMeCheckBox.isChecked()) {
                String uName = userName.getText().toString();
                String uPass = userPass.getText().toString();
                PreferencesHelper.storeUserName(context, uName);
                PreferencesHelper.storeUserPass(context, uPass);
                PreferencesHelper.storeIsUserLogin(context, true);
            }
            PreferencesHelper.storeUserSessionId(context, sessionId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (userActionsCallBack != null) userActionsCallBack.loginAction();
    }
}

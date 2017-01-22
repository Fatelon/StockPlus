package com.fatelon.stocksplus.view.fragments;

import android.content.Context;
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
import com.fatelon.stocksplus.model.dto.RegistrationDTO;
import com.fatelon.stocksplus.view.callbacks.LoadingCallBack;
import com.fatelon.stocksplus.view.callbacks.PressBackCallBack;
import com.fatelon.stocksplus.view.callbacks.SimpleDialogCallback;
import com.fatelon.stocksplus.view.customviews.CustomTitle;
import com.fatelon.stocksplus.view.dialogs.SimpleDialog;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.fatelon.stocksplus.Constants.SERVER_MESSAGE_ERROR;

/**
 * Created by User on 20.01.2017.
 */

public class RegistrationScreen extends BaseFragment implements SimpleDialogCallback {

    private static String TAG = RegistrationScreen.class.getSimpleName();

    private CustomTitle customTitle;

    private Button registrationSignUpButton;

    private EditText registrationFirstName;
    private EditText registrationLastName;
    private EditText registrationEmail;
    private EditText registrationPhone;
    private EditText registrationUserName;
    private EditText registrationPassword;
    private EditText registrationConfirmPassword;

    private PressBackCallBack pressBackCallBack = null;
    private SimpleDialogCallback simpleDialogCallback = null;
    private LoadingCallBack loadingCallBack = null;

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
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        customTitle = (CustomTitle) view.findViewById(R.id.registration_title);
        customTitle.setPressBackCallBack(context);
        registrationSignUpButton = (Button) view.findViewById(R.id.registration_sign_up);
        registrationSignUpButton.setOnClickListener(v -> onRegistrationSignUpButtonClick(v));
        registrationFirstName = (EditText) view.findViewById(R.id.registration_first_name);
        registrationLastName = (EditText) view.findViewById(R.id.registration_last_name);
        registrationEmail = (EditText) view.findViewById(R.id.registration_email);
        registrationPhone = (EditText) view.findViewById(R.id.registration_phone);
        registrationUserName = (EditText) view.findViewById(R.id.registration_user_name);
        registrationPassword = (EditText) view.findViewById(R.id.registration_password);
        registrationConfirmPassword = (EditText) view.findViewById(R.id.registration_confirm_password);
        pressBackCallBack = (PressBackCallBack) context;
        simpleDialogCallback = (SimpleDialogCallback) this;
    }

    private void onRegistrationSignUpButtonClick(View view) {
        if (checkFieldsIsEmpty()) {
            ErrorHelper.registrationFillingIncorrect(context);
        } else {
            if (loadingCallBack != null) loadingCallBack.showLoading();
            ApiInterface apiInterface = ApiModule.getApiInterface();
            apiInterface.postRegistration(getDataMap()).subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Subscriber<RegistrationDTO>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            ErrorHelper.failedToConnectSimpleDialog(context, e);
                            if (loadingCallBack != null) loadingCallBack.hideLoading();
                        }

                        @Override
                        public void onNext(RegistrationDTO user) {
                            Log.d(TAG, "onNext - ");
                            if (loadingCallBack != null) loadingCallBack.hideLoading();
                            try {
                                int error = user.getError();
                                if (error == SERVER_MESSAGE_ERROR) {
                                    ErrorHelper.registrationErrorFromServer(context);
                                } else {
                                    String title = context.getResources().getString(R.string.registration_register_title);
                                    String message = context.getResources().getString(R.string.registration_register_message);
                                    SimpleDialog.showSimpleDialogWithCallback(context, title, message, simpleDialogCallback);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
        }
    }

    private boolean checkFieldsIsEmpty() {
        return (registrationFirstName.getText().toString().isEmpty() ||
                registrationLastName.getText().toString().isEmpty() ||
                registrationEmail.getText().toString().isEmpty() ||
                registrationPhone.getText().toString().isEmpty() ||
                registrationUserName.getText().toString().isEmpty() ||
                registrationPassword.getText().toString().isEmpty() ||
                registrationConfirmPassword.getText().toString().isEmpty() ||
                !registrationPassword.getText().toString().equals(registrationConfirmPassword.getText().toString()));
    }

    private Map<String, String> getDataMap() {
        String uFirstName = registrationFirstName.getText().toString();
        String uLastName = registrationLastName.getText().toString();
        String uEmail = registrationEmail.getText().toString();
        String uPhone = registrationPhone.getText().toString();
        String uName = registrationUserName.getText().toString();
        String uPassword = registrationPassword.getText().toString();
        Map<String, String> m = new HashMap<>();
        m.put(context.getResources().getString(R.string.f_name), uFirstName);
        m.put(context.getResources().getString(R.string.l_name), uLastName);
        m.put(context.getResources().getString(R.string.email), uEmail);
        m.put(context.getResources().getString(R.string.phone), uPhone);
        m.put(context.getResources().getString(R.string.user_name), uName);
        m.put(context.getResources().getString(R.string.user_password), uPassword);
        return m;
    }

    @Override
    public void simpleDialogReaction() {
        if (pressBackCallBack != null) pressBackCallBack.onPressBack();
    }
}

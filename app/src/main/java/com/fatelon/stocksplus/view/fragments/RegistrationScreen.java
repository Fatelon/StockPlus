package com.fatelon.stocksplus.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatelon.stocksplus.R;

import butterknife.ButterKnife;

/**
 * Created by User on 20.01.2017.
 */

public class RegistrationScreen extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this, view);


        return view;
    }
}

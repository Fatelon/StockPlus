package com.fatelon.stocksplus.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatelon.stocksplus.R;

/**
 * Created by User on 21.01.2017.
 */

public class Portfolio extends BaseMenuFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);

        return view;
    }
}
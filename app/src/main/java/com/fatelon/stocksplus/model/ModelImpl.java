package com.fatelon.stocksplus.model;

import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;

/**
 * Created by Fatelon on 19.01.2017.
 */

public class ModelImpl implements Model {

    ApiInterface apiInterface = ApiModule.getApiInterface();

//    public Observable<LoginDTO> postLogin(String name) {
//        Gson gson = new GsonBuilder().create();
//        return apiInterface.postLogin();
//    }

}

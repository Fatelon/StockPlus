package com.fatelon.stocksplus.model.api;

import com.fatelon.stocksplus.model.dto.LoginDTO;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by User on 19.01.2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("?action=login_user")
    Observable<LoginDTO> postLogin( @FieldMap Map<String, String> userParams);

//    public static final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
}

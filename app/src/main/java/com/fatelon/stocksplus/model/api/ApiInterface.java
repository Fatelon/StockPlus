package com.fatelon.stocksplus.model.api;

import com.fatelon.stocksplus.model.dto.LoginDTO;
import com.fatelon.stocksplus.model.dto.RegistrationDTO;
import com.fatelon.stocksplus.model.dto.SignalsDTO;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by User on 19.01.2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("?action=login_user")
    Observable<LoginDTO> postLogin(@FieldMap Map<String, String> userParams);

    @FormUrlEncoded
    @POST("?action=register_user")
    Observable<RegistrationDTO> postRegistration(@FieldMap Map<String, String> userParams);

    @GET("?action=get_signals_list&type={type}")
    Observable<SignalsDTO> getSignals(@Path("type") String type);
}

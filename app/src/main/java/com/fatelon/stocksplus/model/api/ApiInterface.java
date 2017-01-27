package com.fatelon.stocksplus.model.api;

import com.fatelon.stocksplus.model.dto.calendar.WeekCalendarDTO;
import com.fatelon.stocksplus.model.dto.quotes.AddNewQuoteDTO;
import com.fatelon.stocksplus.model.dto.indexes.IndexesDTO;
import com.fatelon.stocksplus.model.dto.LoginDTO;
import com.fatelon.stocksplus.model.dto.news.NewsDTO;
import com.fatelon.stocksplus.model.dto.RegistrationDTO;
import com.fatelon.stocksplus.model.dto.signals.SignalsDTO;
import com.fatelon.stocksplus.model.dto.quotes.UserDataDTO;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Fatelon on 19.01.2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("?action=login_user")
    Observable<LoginDTO> postLogin(@FieldMap Map<String, String> userParams);

    @FormUrlEncoded
    @POST("?action=register_user")
    Observable<RegistrationDTO> postRegistration(@FieldMap Map<String, String> userParams);

    @GET("?action=get_signals_list")
    Observable<SignalsDTO> getSignals(@Query("type") String type);

    @GET("?action=get_3_indexes")
    Observable<IndexesDTO> getIndexes();

    @GET("?action=get_news_list")
    Observable<NewsDTO> getNews(@Query("limit") Integer limit);

    @GET("?action=get_user_data&filter=quotes")
    Observable<UserDataDTO> getUserData(@Query("user_id") Integer userId);

    @GET("?action=delete_logged_user_quote")
    Observable<LoginDTO> deleteQuote(@Query("id") String crossId, @Query("PHPSESSID") String session_id);

    @FormUrlEncoded
    @POST("?action=add_logged_user_quote")
    Observable<AddNewQuoteDTO> postNewQuote(@FieldMap Map<String, String> params, @Query("PHPSESSID") String session_id);


    @GET("?action=current_week_calendar")
    Observable<WeekCalendarDTO> getWeekCalendar();
}

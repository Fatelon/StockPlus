package com.fatelon.stocksplus.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.calendar.CalendarDTO;
import com.fatelon.stocksplus.model.dto.calendar.WeekCalendarDTO;
import com.fatelon.stocksplus.model.dto.news.NewsDTO;
import com.fatelon.stocksplus.model.dto.news.OneNewsDTO;
import com.fatelon.stocksplus.view.customviews.CustomCalendarBox;
import com.fatelon.stocksplus.view.customviews.CustomTextView;
import com.fatelon.stocksplus.view.customviews.customRecyclerView.StickyHeaderLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static com.fatelon.stocksplus.Constants.NEWS_ALL_LIMIT;
import static com.fatelon.stocksplus.Constants.NEWS_TEXT_MAX_LENGTH;

/**
 * Created by Fatelon on 21.01.2017.
 */

public class News extends BaseMenuFragment {

    private static String TAG = News.class.getSimpleName();

    private DayEvents.DayEventsListViewAdapter customEventsListViewAdapter;

    private final PublishSubject<String> onClickEventSubject = PublishSubject.create();

    private final PublishSubject<String> onClickNews = PublishSubject.create();

    private Map<String, CalendarDTO> calendar = new TreeMap<String, CalendarDTO>();

    private RecyclerView recyclerView;

    private FrameLayout calendarLoadingIndicator;
    private FrameLayout newsLoadingIndicator;

    private CustomTextView newsDayButton;
    private CustomTextView newsMonthButton;

    private ListView newsList;
    private OneNewsListViewAdapter newsListViewAdapter;
    private List<OneNewsDTO> newsData = new ArrayList<OneNewsDTO>();

    private CustomCalendarBox calendarView;

    private ScrollView calendarBoxScrollView;

    private String currentDay = "";

    private boolean calendarTypeDay = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        init(view);

        getMonthCalendar();
        getAllNews();
        return view;
    }

    private void init(View view) {
        currentDay = (String) DateFormat.format("dd", new Date());
        try {
            Date d = (new SimpleDateFormat("dd")).parse(currentDay);
            currentDay = (String) DateFormat.format("yyyy-mm-dd HH:mm:ss", d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.news_fragment_recycle_list_view);
        recyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        customEventsListViewAdapter = new DayEvents.DayEventsListViewAdapter(currentDay, calendar);
        customEventsListViewAdapter.getTickerClicks().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                onClickEventSubject.onNext(s);
            }
        });
        recyclerView.setAdapter(customEventsListViewAdapter);
        calendarLoadingIndicator = (FrameLayout) view.findViewById(R.id.news_calendar_loading_indicator);
        newsLoadingIndicator = (FrameLayout) view.findViewById(R.id.news_fragment_news_loading_indicator);
        newsDayButton = (CustomTextView) view.findViewById(R.id.news_day_button);
        newsDayButton.setOnClickListener(v->{changeCalendarType();});
        newsMonthButton = (CustomTextView) view.findViewById(R.id.news_month_button);
        newsMonthButton.setOnClickListener(v->{changeCalendarType();});
        makeButtonRed(newsDayButton);
        makeButtonBlack(newsMonthButton);

        calendarView = (CustomCalendarBox) view.findViewById(R.id.news_custom_calendar_box);
        calendarView.getDayCalendarBoxesClick().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                currentDay = s;
                changeCalendarType();
            }
        });
        calendarBoxScrollView = (ScrollView) view.findViewById(R.id.new_calendar_box_scroll_view);

        newsData.add(new OneNewsDTO());
        newsListViewAdapter = new OneNewsListViewAdapter(context, R.layout.custom_news_item_view, newsData);
        newsList = (ListView) view.findViewById(R.id.news_fragment_news_list_view);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickNews.onNext(newsListViewAdapter.getUrl(position));
            }
        });
        newsList.setAdapter(newsListViewAdapter);
    }

    public Observable<String> getEventClick() {
        return onClickEventSubject.asObservable();
    }

    public Observable<String> getNewsClicks(){
        return onClickNews.asObservable();
    }

    private void changeCalendarType() {
        calendarTypeDay = !calendarTypeDay;
        if (calendarTypeDay) {
            makeButtonRed(newsDayButton);
            makeButtonBlack(newsMonthButton);
        } else {
            makeButtonRed(newsMonthButton);
            makeButtonBlack(newsDayButton);
        }
        setCalendar();
    }

    private void makeButtonBlack(View view) {
        GradientDrawable bgShape = (GradientDrawable)view.getBackground();
        bgShape.setColor(ContextCompat.getColor(context, R.color.mainBackground));
        view.setClickable(true);
    }

    private void makeButtonRed(View view) {
        GradientDrawable bgShape = (GradientDrawable)view.getBackground();
        bgShape.setColor(ContextCompat.getColor(context, R.color.red));
        view.setClickable(false);
    }

    private void setCalendar() {
        if (calendarTypeDay) {
            recyclerView.setVisibility(View.VISIBLE);
            calendarBoxScrollView.setVisibility(View.GONE);
            customEventsListViewAdapter.setNewCalendar(currentDay, calendar);
//            customEventsListViewAdapter.notifyAllSectionsDataSetChanged();
            calendarLoadingIndicator.setVisibility(View.GONE);
        } else {
            calendarBoxScrollView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void getMonthCalendar() {
        calendarLoadingIndicator.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getMonthCalendar().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<WeekCalendarDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                    }

                    @Override
                    public void onNext(WeekCalendarDTO response) {
                        Log.d(TAG, "onNext - ");
                        try {
                            calendar.clear();
                            calendar.putAll(response.getCalendar());
                            setCalendar();
                            calendarView.setDates(calendar, true);
                            calendarLoadingIndicator.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    private void getAllNews() {
        newsLoadingIndicator.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getNews(NEWS_ALL_LIMIT).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<NewsDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                    }

                    @Override
                    public void onNext(NewsDTO news) {
                        Log.d(TAG, "onNext - ");
                        try {
                            newsData.clear();
                            newsData.addAll(news.getNews());
                            newsListViewAdapter.notifyDataSetChanged();
                            newsLoadingIndicator.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    public class OneNewsListViewAdapter extends ArrayAdapter<OneNewsDTO> {

        private final Context context;

        private List<OneNewsDTO> newsData;

        private final Integer resource;

        public OneNewsListViewAdapter (Context context, int resource, List<OneNewsDTO> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.newsData = objects;
        }

        @Override
        public int getCount() {
            return newsData.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public String getUrl(int position) {
            String url = "";
            try {
                url = newsData.get(position).getUrl();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return url;
        }

        public class ViewHolder
        {
            CustomTextView itemText;
            CustomTextView itemTime;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            View rowView = view;
            final ViewHolder viewHolder;
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.custom_news_item_view, null, true);
                viewHolder = new ViewHolder();
                viewHolder.itemText = (CustomTextView) rowView.findViewById(R.id.custom_news_item_text);
                viewHolder.itemTime = (CustomTextView) rowView.findViewById(R.id.custom_news_item_time);
                RelativeLayout container = (RelativeLayout) rowView.findViewById(R.id.custom_news_item_main_container);
//                container.setBackgroundColor(ContextCompat.getColor(context, R.color.darkGrayBackground));
                container.setPadding(5, 5, 0, 0);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }
            String text = newsData.get(position).getTitle();
            Integer n = NEWS_TEXT_MAX_LENGTH;
            if (text.length() > n) {
                text = text.substring(0, n);
                text += "..";
            }

            viewHolder.itemText.setText(text);
            viewHolder.itemTime.setText(newsData.get(position).getTime());

            return rowView;
        }
    }
}

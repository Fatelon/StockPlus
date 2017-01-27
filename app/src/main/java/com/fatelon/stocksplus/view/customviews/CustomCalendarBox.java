package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.calendar.CalendarDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Fatelon on 26.01.2017.
 */

public class CustomCalendarBox extends LinearLayout {

    private TableRow calendarDateBox;

    private final PublishSubject<String> onClickDayCalendarBoxSubject = PublishSubject.create();

    public CustomCalendarBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_calendar_box, this);
        calendarDateBox = (TableRow) findViewById(R.id.calendar_date_box);
    }

    public void setDates(Map<String, CalendarDTO> calendar) {
        for (Map.Entry<String, CalendarDTO> entry : calendar.entrySet()) {
            if (entry != null) {
                final String dateString = entry.getValue().getDt();
                Date myDate = new Date();
                try {
                    myDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(dateString);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String finalDate = new SimpleDateFormat("d").format(myDate);
                DayCalendarBox dayCalendarBox = new DayCalendarBox(getContext());
                dayCalendarBox.setDayText(finalDate);
                dayCalendarBox.setOnClickListener(v->{onClickDayCalendarBoxSubject.onNext(dateString);});
                if (entry.getValue().getConfcalls() != null) dayCalendarBox.setColors(1);
                if (entry.getValue().getDividents() != null) dayCalendarBox.setColors(2);
                if (entry.getValue().getEarnings() != null) dayCalendarBox.setColors(3);
                if (entry.getValue().getSplits() != null) dayCalendarBox.setColors(4);
                if (entry.getValue().getIPO() != null) dayCalendarBox.setColors(5);

                calendarDateBox.addView(dayCalendarBox);
            }
        }
    }

    public Observable<String> getDayCalendarBoxesClick(){
        return onClickDayCalendarBoxSubject.asObservable();
    }
}

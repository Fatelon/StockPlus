package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.calendar.CalendarDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Fatelon on 26.01.2017.
 */

public class CustomCalendarBox extends LinearLayout {

    private TableLayout calendarDateBox;

    private Context context;

    private final PublishSubject<String> onClickDayCalendarBoxSubject = PublishSubject.create();

    public CustomCalendarBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_calendar_box, this);
//        calendarDateBox = (TableLayout) findViewById(R.id.calendar_date_box);
    }

    public void setDates(Map<String, CalendarDTO> calendar, boolean isMonth) {
        calendarDateBox = (TableLayout) findViewById(R.id.calendar_date_box);
        int day = 1;
        TableRow newRow = getNewRow();
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

                if (isMonth) {
                    isMonth = !isMonth;
                    Calendar c = Calendar.getInstance();
                    c.setTime(myDate);
                    int dayOfWeek = (c.get(Calendar.DAY_OF_WEEK) + 6) % 8;

                    for (int i = 1; i < dayOfWeek; i++) {
                        DayCalendarBox emptyDay = new DayCalendarBox(getContext());
                        emptyDay.setVisibility(View.INVISIBLE);
                        emptyDay.setColors(1);
                        emptyDay.setGravity(Gravity.CENTER);
                        emptyDay.setClickable(false);
                        newRow.addView(emptyDay);
                        day++;
                    }
                }
                DayCalendarBox dayCalendarBox = new DayCalendarBox(getContext());
                dayCalendarBox.setDayText(finalDate);
                dayCalendarBox.setOnClickListener(v->{onClickDayCalendarBoxSubject.onNext(dateString);});
                if (entry.getValue().getConfcalls() != null) dayCalendarBox.setColors(1);
                if (entry.getValue().getDividents() != null) dayCalendarBox.setColors(2);
                if (entry.getValue().getEarnings() != null) dayCalendarBox.setColors(3);
                if (entry.getValue().getSplits() != null) dayCalendarBox.setColors(4);
                if (entry.getValue().getIPO() != null) dayCalendarBox.setColors(5);
                dayCalendarBox.setGravity(Gravity.CENTER);
                newRow.addView(dayCalendarBox);
                day++;
                if (day > 7) {
                    day = 1;
                    calendarDateBox.addView(newRow);
                    newRow = getNewRow();
                }
            }
        }
        if (day > 1) {
            calendarDateBox.addView(newRow);
        }
    }

    private TableRow getNewRow() {
        TableRow newRow = new TableRow(context);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 30, 0, 30);
        newRow.setLayoutParams(params);
        newRow.setWeightSum(7);
        return newRow;
    }

    public Observable<String> getDayCalendarBoxesClick(){
        return onClickDayCalendarBoxSubject.asObservable();
    }
}

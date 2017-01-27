package com.fatelon.stocksplus.view.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fatelon.stocksplus.R;

/**
 * Created by Fatelon on 27.01.2017.
 */

public class DayCalendarBox extends LinearLayout {

    private CustomTextView dayCalendarBoxText;

    private View day_calendar_stripe_1;
    private View day_calendar_stripe_2;
    private View day_calendar_stripe_3;
    private View day_calendar_stripe_4;
    private View day_calendar_stripe_5;

    public DayCalendarBox(Context context) {
        super(context);
        init(context);
    }

    public DayCalendarBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setDayText(String text) {
        dayCalendarBoxText.setText(text);
    }

    public void setColors(int i) {
        switch (i) {
            case 1: day_calendar_stripe_1.setVisibility(VISIBLE); break;
            case 2: day_calendar_stripe_2.setVisibility(VISIBLE); break;
            case 3: day_calendar_stripe_3.setVisibility(VISIBLE); break;
            case 4: day_calendar_stripe_4.setVisibility(VISIBLE); break;
            case 5: day_calendar_stripe_5.setVisibility(VISIBLE); break;
        }
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.day_calendar_box, this);
        dayCalendarBoxText = (CustomTextView) findViewById(R.id.day_calendar_box_text);
        day_calendar_stripe_1 = (View) findViewById(R.id.day_calendar_stripe_1);
        day_calendar_stripe_2 = (View) findViewById(R.id.day_calendar_stripe_2);
        day_calendar_stripe_3 = (View) findViewById(R.id.day_calendar_stripe_3);
        day_calendar_stripe_4 = (View) findViewById(R.id.day_calendar_stripe_4);
        day_calendar_stripe_5 = (View) findViewById(R.id.day_calendar_stripe_5);
    }

}

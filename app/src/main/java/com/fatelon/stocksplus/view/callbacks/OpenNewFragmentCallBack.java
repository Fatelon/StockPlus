package com.fatelon.stocksplus.view.callbacks;

import com.fatelon.stocksplus.model.dto.calendar.CalendarDTO;

import java.util.Map;

/**
 * Created by Fatelon on 23.01.2017.
 */

public interface OpenNewFragmentCallBack {
    public void openNewFragment(Integer number);

    public void openNewFragmentWithString(Integer number, String param);

    public void openNewFragmentWithWeekCalendar(Integer number, Map<String, CalendarDTO> calendar);
}

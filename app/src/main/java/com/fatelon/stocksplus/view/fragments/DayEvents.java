package com.fatelon.stocksplus.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.calendar.CalendarDTO;
import com.fatelon.stocksplus.model.dto.calendar.CommonEventDTO;
import com.fatelon.stocksplus.model.dto.calendar.DividentDTO;
import com.fatelon.stocksplus.model.dto.calendar.EarningDTO;
import com.fatelon.stocksplus.view.customviews.CustomTextView;
import com.fatelon.stocksplus.view.customviews.CustomTitle;
import com.fatelon.stocksplus.view.customviews.customRecyclerView.SectioningAdapter;
import com.fatelon.stocksplus.view.customviews.customRecyclerView.StickyHeaderLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by Fatelon on 27.01.2017.
 */

public class DayEvents extends BaseFragment {

    private RecyclerView recyclerView;

    private CustomTitle customTitle;

    private Map<String, CalendarDTO> calendar = new HashMap<String, CalendarDTO>();

    private final PublishSubject<String> onClickEventSubject = PublishSubject.create();

    private String currentDay = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_events, container, false);
        init(view);

        return view;
    }

    public void setCalendar(String currentDay, Map<String, CalendarDTO> calendar) {
        this.currentDay = currentDay;
        this.calendar = calendar;
    }

    public Observable<String> getEventClick() {
        return onClickEventSubject.asObservable();
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.day_events_recycler_view);
        recyclerView.setLayoutManager(new StickyHeaderLayoutManager());

        customTitle = (CustomTitle) view.findViewById(R.id.day_events_title);
        customTitle.setPressBackCallBack(context);

        DayEventsListViewAdapter customEventsListViewAdapter = new DayEventsListViewAdapter(currentDay, calendar);
        customEventsListViewAdapter.getTickerClicks().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                onClickEventSubject.onNext(s);
            }
        });
        recyclerView.setAdapter(customEventsListViewAdapter);
    }

    public static class DayEventsListViewAdapter extends SectioningAdapter {

        private List<DayEventsListViewAdapter.Section> sections = new ArrayList<DayEventsListViewAdapter.Section>();

        private final PublishSubject<String> onClickSubject = PublishSubject.create();

        public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
            View color;
            View eventsListDivider;
            CustomTextView ticker;
            CustomTextView title;
            CustomTextView dividend;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ticker = (CustomTextView) itemView.findViewById(R.id.events_item_ticker);
                title = (CustomTextView) itemView.findViewById(R.id.events_item_title);
                dividend = (CustomTextView) itemView.findViewById(R.id.events_item_dividend);
                color = (View) itemView.findViewById(R.id.events_item_colored_stripe);
                eventsListDivider = (View) itemView.findViewById(R.id.events_list_divider);
            }
        }

        public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
            TextView textView;

            public HeaderViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.events_header_text);
            }
        }


        class Section {
            String text;
            ArrayList<Event> items = new ArrayList<>();

            public Section(String text) {
                this.text = text;
            }

            public ArrayList<Event> getItems() {
                return items;
            }
        }

        class Event {
            int type;
            String ticker;
            String title;
            String dividend;

            public Event(int type, String ticker, String title, String dividend) {
                this.type = type;
                this.ticker = ticker;
                this.title = title;
                this.dividend = dividend;
            }
        }

        public DayEventsListViewAdapter(String currentDay, Map<String, CalendarDTO> calendar) {
            setNewCalendar(currentDay, calendar);
        }

        public void setNewCalendar(String currentDay, Map<String, CalendarDTO> calendar) {
            sections.clear();
            if (calendar != null) {
                for (Map.Entry<String, CalendarDTO> entry : calendar.entrySet()) {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    Date myDate1 = new Date();
                    Date myDate2 = new Date();
                    try {
                        myDate1 = dateFormat.parse(entry.getValue().getDt());
                        myDate2 = dateFormat.parse(currentDay);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    String day1 = (String) DateFormat.format("dd", myDate1);
                    String day2 = (String) DateFormat.format("dd", myDate2);
                    if (entry != null && day1.equals(day2)) {
//                    if (entry != null && entry.getValue().getDt() == currentDay) {
                        Section section = new Section("Conference Calls");
                        setCommonItems(8, section, entry.getValue().getConfcalls());
                        sections.add(section);
                        section = new Section("Dividends");
                        List<DividentDTO> divid = entry.getValue().getDividents();
                        if (divid != null) {
                            for (DividentDTO ern : divid) {
                                if (ern != null) {
                                    section.items.add(new Event(9, ern.getTicker(), ern.getCompany(), "Dividends: " + ern.getDivident() + " $"));
                                }
                            }
                        }
                        sections.add(section);
                        section = new Section("Earnings");
                        List<EarningDTO> earn = entry.getValue().getEarnings();
                        if (earn != null) {
                            for (EarningDTO ern : earn) {
                                if (ern != null) {
                                    section.items.add(new Event(7, ern.getTicker(), ern.getCompany(), ""));
                                }
                            }
                        }
                        sections.add(section);
                        section = new Section("Splits");
                        setCommonItems(10, section, entry.getValue().getSplits());
                        sections.add(section);
                        section = new Section("IPO");
                        setCommonItems(11, section, entry.getValue().getIPO());
                        sections.add(section);
                    }
                }
            }
            notifyAllSectionsDataSetChanged();
        }

        private void setCommonItems(int type, Section section, List<CommonEventDTO> comItems) {
            if (comItems != null) {
                for (CommonEventDTO ern : comItems) {
                    if (ern != null) {
                        String time = ern.getTime().replace("?", "");
                        section.items.add(new Event(type, ern.getTicker(), ern.getTitle(), time));
                    }
                }
            }
        }

        @Override
        public int getNumberOfSections() {
            return sections.size();
        }

        @Override
        public int getNumberOfItemsInSection(int sectionIndex) {
            return sections.get(sectionIndex).items.size();
        }

        @Override
        public boolean doesSectionHaveHeader(int sectionIndex) {
            return true;
        }

        @Override
        public boolean doesSectionHaveFooter(int sectionIndex) {
            return false;
        }

        @Override
        public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ItemViewHolder(inflater.inflate(R.layout.events_list_view_item, parent, false));
        }

        @Override
        public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new HeaderViewHolder(inflater.inflate(R.layout.events_list_view_header, parent, false));
        }

        @Override
        public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
            Section s = sections.get(sectionIndex);
            HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
            hvh.textView.setText(s.text);
        }

        @Override
        public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
            Section s = sections.get(sectionIndex);
            ItemViewHolder ivh = (ItemViewHolder) viewHolder;
            ivh.ticker.setText(s.items.get(itemIndex).ticker);
            ivh.title.setText(s.items.get(itemIndex).title);
            ivh.dividend.setText(s.items.get(itemIndex).dividend);
            switch (s.items.get(itemIndex).type) {
                case 7: ivh.color.setBackgroundResource(R.color.colored_stripe_earnings); break;
                case 8:
                    ivh.color.setBackgroundResource(R.color.colored_stripe_conference);
                    ivh.title.setVisibility(View.GONE);
                    break;
                case 9: ivh.color.setBackgroundResource(R.color.colored_stripe_dividends); break;
                case 10: ivh.color.setBackgroundResource(R.color.colored_stripe_splits); break;
                case 11: ivh.color.setBackgroundResource(R.color.colored_stripe_ipo); break;
            }
//            if (itemIndex == s.items.size() - 1) {
//                ivh.eventsListDivider.setVisibility(View.GONE);
//            }

            final String element = s.items.get(itemIndex).ticker;
            ivh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSubject.onNext(element);
                }
            });
        }

        public Observable<String> getTickerClicks(){
            return onClickSubject.asObservable();
        }
    }
}

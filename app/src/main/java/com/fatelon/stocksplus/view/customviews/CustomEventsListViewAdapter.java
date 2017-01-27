package com.fatelon.stocksplus.view.customviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.calendar.CalendarDTO;
import com.fatelon.stocksplus.model.dto.calendar.CommonEventDTO;
import com.fatelon.stocksplus.model.dto.calendar.DividentDTO;
import com.fatelon.stocksplus.model.dto.calendar.EarningDTO;
import com.fatelon.stocksplus.view.customviews.customRecyclerView.SectioningAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;

import static com.fatelon.stocksplus.Constants.DIVIDENDS_TRIGGER;

/**
 * Created by Fatelon on 27.01.2017.
 */

public class CustomEventsListViewAdapter extends SectioningAdapter {

    private List<Section> sections = new ArrayList<Section>();

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

    public CustomEventsListViewAdapter(int eventType, Map<String, CalendarDTO> calendar) {
        if (calendar != null) {
            for (Map.Entry<String, CalendarDTO> entry : calendar.entrySet()) {
                if (entry != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    Date myDate = new Date();
                    try {
                        myDate = dateFormat.parse(entry.getValue().getDt());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    SimpleDateFormat timeFormat = new SimpleDateFormat("c d LLL yy");
                    String finalDate = timeFormat.format(myDate);
                    Section section = new Section(finalDate);
                    if (eventType == DIVIDENDS_TRIGGER) {
                        List<DividentDTO> dividentDTOs = new ArrayList<>();
                        try {
                            dividentDTOs = entry.getValue().getDividents();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (dividentDTOs != null) {
                            for (DividentDTO ern : dividentDTOs) {
                                if (ern != null) {
                                    section.items.add(new Event(eventType, ern.getTicker(), ern.getCompany(), "Dividends: " + ern.getDivident() + " $"));
                                }
                            }
                        }
                    } else if (eventType == 7) {
                        List<EarningDTO> earningDTOs = new ArrayList<>();
                        earningDTOs = entry.getValue().getEarnings();
                        if (earningDTOs != null) {
                            for (EarningDTO ern : earningDTOs) {
                                if (ern != null) {
                                    section.items.add(new Event(eventType, ern.getTicker(), ern.getCompany(), ""));
                                }
                            }
                        }
                    } else {
                        List<CommonEventDTO> commonDTOs = new ArrayList<CommonEventDTO>();
                        switch (eventType) {
                            case 8: commonDTOs = entry.getValue().getConfcalls(); break;
                            case 10: commonDTOs = entry.getValue().getSplits(); break;
                            case 11: commonDTOs = entry.getValue().getIPO(); break;
                        }
                        if (commonDTOs != null) {
                            for (CommonEventDTO ern : commonDTOs) {
                                if (ern != null) {
                                    String time = ern.getTime().replace("?", "");
                                    section.items.add(new Event(eventType, ern.getTicker(), ern.getTitle(), time));
                                }
                            }
                        }
                    }
                    sections.add(section);
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
        if (itemIndex == s.items.size() - 1) {
            ivh.eventsListDivider.setVisibility(View.GONE);
        }

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

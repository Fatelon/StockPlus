package com.fatelon.stocksplus.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.PreferencesHelper;
import com.fatelon.stocksplus.view.customviews.CustomTextView;
import com.fatelon.stocksplus.view.customviews.CustomTitle;
import com.fatelon.stocksplus.view.customviews.customRecyclerView.SectioningAdapter;
import com.fatelon.stocksplus.view.customviews.customRecyclerView.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fatelon on 29.01.2017.
 */

public class IndicatorsFragment extends BaseFragment {

    private Integer indicatorType = 1;

    private CustomTitle customTitle;

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_indicators, container, false);


        Bundle args = getArguments();
        indicatorType = args.getInt("indicator_type");

        init(view);

        return view;
    }

    private void init(View view) {
        customTitle = (CustomTitle) view.findViewById(R.id.indicators_title);
        if (indicatorType == 1) {
            customTitle.setCustomText(context.getResources().getString(R.string.first_indicators_title_short));
        } else if (indicatorType == 2) {
            customTitle.setCustomText(context.getResources().getString(R.string.second_indicators_title_short));
        }
        customTitle.setPressBackCallBack(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.indicators_recycler_view);
        recyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        IndicatorsListViewAdapter customEventsListViewAdapter = new IndicatorsListViewAdapter(indicatorType == 1);
        recyclerView.setAdapter(customEventsListViewAdapter);

    }

    //////////////////////////////

    public class IndicatorsListViewAdapter extends SectioningAdapter {

        private List<Section> sections = new ArrayList<Section>();

        private boolean indicatorsType = true;

//        private final PublishSubject<Integer> onClickItem = PublishSubject.create();

        public class ItemViewHolder extends SectioningAdapter.ItemViewHolder{
            CustomTextView textView;
            View divider;

            public ItemViewHolder(View itemView) {
                super(itemView);
                textView = (CustomTextView) itemView.findViewById(R.id.indicators_list_item_text);
                divider = (View) itemView.findViewById(R.id.indicators_list_divider);
            }

        }

        public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
            CustomTextView textView;

            public HeaderViewHolder(View itemView) {
                super(itemView);
                textView = (CustomTextView) itemView.findViewById(R.id.events_header_text);
            }
        }

        class Section {
            String text;
            ArrayList<Item> items = new ArrayList<>();

            public Section(String text) {
                this.text = text;
            }

        }

        class Item {
            String text;
            Integer position;

            public Item(String text, Integer pos) {
                this.text = text;
                this.position = pos;
            }
        }

        public IndicatorsListViewAdapter(boolean firstGroupType) {
            indicatorsType = firstGroupType;
            sections.add(new Section(""));
            sections.add(new Section(""));
            sections.get(0).text = getResources().getString(R.string.first_section_indicators_title);
            sections.get(1).text = getResources().getString(R.string.second_section_indicators_title);
            Integer res;
            if (indicatorsType) {
                res = R.array.first_group_indicators_names;
            } else {
                res = R.array.second_group_indicators_names;
            }
            setFirstSectionItems(res);
            setSecondSectionItems(res);
//            onClickItem.asObservable().subscribe(new Action1<Integer>() {
//                @Override
//                public void call(Integer num) {
//                    addInFirstSection(num);
//                }
//            });
        }

        private  void addInFirstSection(Integer number) {
            if (!isElemInFirstSection(number)) {
                Item it = new Item(sections.get(1).items.get(number).text, number);
                sections.get(0).items.add(it);
                updateIndicatorsInfo();
                notifyAllSectionsDataSetChanged();
            }
        }

        private  void deleteInFirstSection(Integer number) {
            Item it = sections.get(0).items.get(number);
            sections.get(0).items.remove(it);
            updateIndicatorsInfo();
            notifyAllSectionsDataSetChanged();
        }

        private void updateIndicatorsInfo() {
            String sequence = "";
            for (Item i : sections.get(0).items) {
                sequence += i.position;
            }
            if (indicatorsType) {
                PreferencesHelper.storeFirstIndicators(context, sequence);
            } else {
                PreferencesHelper.storeSecondIndicators(context, sequence);
            }
        }

        private boolean isElemInFirstSection(Integer number) {
            Item it = sections.get(1).items.get(number);
            for (Item i : sections.get(0).items) {
                if (it.position == i.position) {
                    return true;
                }
            }
            return false;
        }

        private void setFirstSectionItems(int res) {
            String sequence = "";
            String[] indicatorsArray = getResources().getStringArray(res);
            if (indicatorsType) {
                sequence = PreferencesHelper.getFirstIndicators(context);
            } else {
                sequence = PreferencesHelper.getSecondIndicators(context);
            }
            for (int i = 0; i < sequence.length(); i++) {
                Integer newPos = Integer.parseInt(sequence.charAt(i) + "");
                if (newPos < indicatorsArray.length) {
                    Item it = new Item(indicatorsArray[newPos], newPos);
                    sections.get(0).items.add(it);
                }
            }
        }

        private void setSecondSectionItems(int res) {
            sections.get(1).items.clear();
            String[] indicatorsArray = getResources().getStringArray(res);
            for (int i = 0; i < indicatorsArray.length; i++) {
                Item it = new Item(indicatorsArray[i], i);
                sections.get(1).items.add(it);
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
            return new ItemViewHolder(inflater.inflate(R.layout.indicators_list_view_item, parent, false));
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
            String str = s.items.get(itemIndex).text;
            ivh.textView.setText(str);

//            if (itemIndex == s.items.size() - 1) {
//                ivh.divider.setVisibility(View.GONE);
//            }

            ivh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sectionIndex == 1) {
                        addInFirstSection(itemIndex);
                    } else {
                        deleteInFirstSection(itemIndex);
                    }
                }
            });
        }

//        public Observable<Integer> getIndicatorsItemClicks(){
//            return onClickItem.asObservable();
//        }

    }
}

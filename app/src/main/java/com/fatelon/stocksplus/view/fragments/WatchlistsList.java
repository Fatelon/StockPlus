package com.fatelon.stocksplus.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.helpers.PreferencesHelper;
import com.fatelon.stocksplus.model.api.ApiInterface;
import com.fatelon.stocksplus.model.api.ApiModule;
import com.fatelon.stocksplus.model.dto.watchlists.GetWatchListsDTO;
import com.fatelon.stocksplus.model.dto.watchlists.WatchListsRespWithErrors;
import com.fatelon.stocksplus.model.dto.watchlists.WatchlistsDTO;
import com.fatelon.stocksplus.view.customviews.CustomTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by Fatelon on 02.02.2017.
 */

public class WatchlistsList extends Fragment {

    private static String TAG = WatchlistsList.class.getSimpleName();

    private Context context;

    private final PublishSubject<String> onClickWLBackButton = PublishSubject.create();

    private List<WatchlistsDTO> watchListsWLData = new ArrayList<WatchlistsDTO>();

    private ListView mWlListView;

    private WatchListsViewAdapter watchListsViewAdapter;

    private ImageView backWLButton;

    private CustomTextView editWLButton;

    private FrameLayout mWLloader;

    private boolean mWLEditMode = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wlists_list, container, false);

        init(view);

        getUserWatchLists();

        return view;
    }

    public Observable<String> getWLBack(){
        return onClickWLBackButton.asObservable();
    }

    private void init(View view) {
        backWLButton = (ImageView) view.findViewById(R.id.watchlist_list_back_button);
        backWLButton.setOnClickListener(v -> {
            onClickWLBackButton.onNext("");});
        editWLButton = (CustomTextView) view.findViewById(R.id.edit_watchlists_list_button);
        editWLButton.setOnClickListener(v -> onClickEditButton());
        mWLloader = (FrameLayout) view.findViewById(R.id.watch_list_loader);

        mWlListView = (ListView) view.findViewById(R.id.w_l_watch_list_list_view);
        watchListsViewAdapter = new WatchListsViewAdapter(context, R.layout.watch_list_view_item, watchListsWLData);
        watchListsViewAdapter.getClickWatchList().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                onClickWLBackButton.onNext(s);
            }
        });
        mWlListView.setAdapter(watchListsViewAdapter);
    }

    private void onClickEditButton() {
        mWLEditMode = !mWLEditMode;
        if (mWLEditMode) {
            editWLButton.setText(context.getResources().getString(R.string.tab_done));
        } else {
            editWLButton.setText(context.getResources().getString(R.string.tab_edit));
        }
        watchListsViewAdapter.changeEdit();
    }

    private void deleteWatchList(String watchListId) {
        mWLloader.setVisibility(View.VISIBLE);
        Map<String, String> m = new HashMap<>();
        m.put("id", watchListId);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.postDeleteWatchList(m, PreferencesHelper.getUserSessionId(context)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<WatchListsRespWithErrors>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                        mWLloader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(WatchListsRespWithErrors response) {
                        Log.d(TAG, "onNext - ");
                        try {
                            getUserWatchLists();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    private void getUserWatchLists() {
        mWLloader.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiModule.getApiInterface();
        apiInterface.getWatchLists(PreferencesHelper.getUserId(context)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<GetWatchListsDTO>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error - " + e.toString());
                        mWLloader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(GetWatchListsDTO response) {
                        Log.d(TAG, "onNext - ");
                        watchListsWLData.clear();
                        try {
                            Map<String, WatchlistsDTO> m = response.getWatchlists();
                            if (m != null) {
                                for (Map.Entry<String, WatchlistsDTO> entry : m.entrySet()) {
                                    watchListsWLData.add(entry.getValue());
                                }
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        watchListsViewAdapter.notifyDataSetChanged();
                        mWLloader.setVisibility(View.GONE);
                    }
                });
    }

    public class WatchListsViewAdapter extends ArrayAdapter<WatchlistsDTO> {

        private final PublishSubject<String> onClickWatchList = PublishSubject.create();

        private final Context context;

        private float dpHeight;
        private float dpWidth;

        private List<WatchlistsDTO> tickersData;

        private final Integer resource;

        private boolean addMinus = false;

        private Integer deleteAppear = -1;
        private Integer deleteDisappear = -1;
        private Integer widthShift = 0;

        public WatchListsViewAdapter (Context context, int resource, List<WatchlistsDTO> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.tickersData = objects;
            getScreenSize(context);
        }

        @Override
        public int getCount() {
            return tickersData.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void changeEdit() {
            addMinus = !addMinus;
            setAppear(-1);
            notifyDataSetChanged();
        }

        public void setAppear(Integer position) {
            deleteDisappear = deleteAppear;
            if (deleteAppear != -1) {
                deleteAppear = -1;
            } else {
                deleteAppear = position;
            }
            notifyDataSetChanged();
        }

        public class ViewHolder {

            RelativeLayout mainContainer;
            ImageView minus;
            CustomTextView nameText;
            CustomTextView delete;
        }

        public Observable<String> getClickWatchList(){
            return onClickWatchList.asObservable();
        }

        private void getScreenSize(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            dpHeight = displayMetrics.heightPixels;
            dpWidth = displayMetrics.widthPixels;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            View rowView = view;
            final ViewHolder viewHolder;
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(resource, null, true);
                viewHolder = new ViewHolder();
                rowView.setTag(viewHolder);
                viewHolder.mainContainer = (RelativeLayout) rowView.findViewById(R.id.watch_lists_l_v_main_cont);
                viewHolder.mainContainer.setTag(position);
                viewHolder.mainContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (addMinus) {
                            setAppear(-1);
                        } else {
                            PreferencesHelper.storeCurrentWatchlistId(context, tickersData.get((Integer)v.getTag()).getId());
                            onClickWatchList.onNext("");
                        }
                    }
                });
                viewHolder.mainContainer.getLayoutParams().width = (int) dpWidth;
                viewHolder.minus = (ImageView) rowView.findViewById(R.id.watch_lists_l_v_minus);
                viewHolder.minus.setTag(position);
                viewHolder.minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAppear((Integer)v.getTag());
                    }
                });
                viewHolder.nameText = (CustomTextView) rowView.findViewById(R.id.watch_list_l_v_name);
                viewHolder.delete = (CustomTextView) rowView.findViewById(R.id.watch_list_l_v_delete);
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAppear(-1);
                        if (deleteDisappear != -1) {
                            String wlId = tickersData.get(deleteDisappear).getId();
                            deleteWatchList(wlId);
                        }
                    }
                });
                widthShift = viewHolder.delete.getLayoutParams().width;
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }
            viewHolder.nameText.setText(tickersData.get(position).getName());
            if (addMinus) {
                viewHolder.minus.setVisibility(View.VISIBLE);
            } else {
                viewHolder.minus.setVisibility(View.GONE);
            }
            if (deleteAppear == position) {
                final View animView = viewHolder.mainContainer;
                final View delView = viewHolder.delete;
                Animation animation = new TranslateAnimation(0, -widthShift, 0, 0);
                animation.setDuration(500);
                animation.setFillAfter(true);
                animation.setAnimationListener(new TranslateAnimation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        delView.setVisibility(View.VISIBLE);
                    }
                });
                animView.startAnimation(animation);
            }
            if (deleteDisappear == position) {
                final View animView = viewHolder.mainContainer;
                viewHolder.delete.setVisibility(View.GONE);
                Animation animation = new TranslateAnimation(-widthShift, 0, 0, 0);
                animation.setDuration(500);
                animation.setFillAfter(true);
                animView.startAnimation(animation);
            }
            return rowView;
        }
    }
}

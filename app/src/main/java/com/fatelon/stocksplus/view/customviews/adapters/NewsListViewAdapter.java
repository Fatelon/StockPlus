package com.fatelon.stocksplus.view.customviews.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.news.NewsFinvizDTO;
import com.fatelon.stocksplus.view.customviews.CustomTextView;

import java.util.List;

import static com.fatelon.stocksplus.Constants.NEWS_TEXT_MAX_LENGTH;

/**
 * Created by Fatelon on 30.01.2017.
 */

public class NewsListViewAdapter extends ArrayAdapter<NewsFinvizDTO> {

    private final Context context;

    private List<NewsFinvizDTO> newsData;

    private final Integer resource;

    public NewsListViewAdapter (Context context, int resource, List<NewsFinvizDTO> objects) {
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
            url = newsData.get(position).getNewsUrl();
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
            container.setBackgroundColor(ContextCompat.getColor(context, R.color.darkGrayBackground));
            container.setPadding(5, 10, 0, 5);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        String text = newsData.get(position).getNewsTitle();
        Integer n = NEWS_TEXT_MAX_LENGTH;
        if (text.length() > n) {
            text = text.substring(0, n);
            text += "..";
        }

        viewHolder.itemText.setText(text);
        viewHolder.itemTime.setText(newsData.get(position).getDt());

        return rowView;
    }
}

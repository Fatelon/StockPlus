package com.fatelon.stocksplus.view.customviews;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.model.dto.signals.OneSignalDTO;

import java.util.List;

/**
 * Created by Fatelon on 24.01.2017.
 */

public class CustomSplitListViewAdapter extends ArrayAdapter<OneSignalDTO> {

    private final Context context;
    private List<OneSignalDTO> splitItemObjects;
    private final Integer resource;

    public CustomSplitListViewAdapter(Context context, int resource, List<OneSignalDTO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.splitItemObjects = objects;
    }

    @Override
    public int getCount() {
        return splitItemObjects.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.custom_split_item, null, true);
        }
        if (getItem(position) != null) {
            CustomTextView ticker = (CustomTextView) rowView.findViewById(R.id.split_item_ticker);
            CustomTextView sector = (CustomTextView) rowView.findViewById(R.id.split_item_sector);
            CustomTextView change = (CustomTextView) rowView.findViewById(R.id.split_item_change);
            CustomTextView company = (CustomTextView) rowView.findViewById(R.id.split_item_company);
            CustomTextView capitalization = (CustomTextView) rowView.findViewById(R.id.split_item_capitalization);
            CustomTextView price = (CustomTextView) rowView.findViewById(R.id.split_item_price);
            CustomTextView volume = (CustomTextView) rowView.findViewById(R.id.split_item_volume);
            ticker.setText(splitItemObjects.get(position).getTicker());
            sector.setText(splitItemObjects.get(position).getSector());
            change.setText(splitItemObjects.get(position).getChange());
            if (splitItemObjects.get(position).getChange().length() > 0 && splitItemObjects.get(position).getChange().charAt(0) == '-') {
                change.setTextColor(ContextCompat.getColor(context, R.color.red));
            }
            company.setText(splitItemObjects.get(position).getCompany());
            capitalization.setText(splitItemObjects.get(position).getMarketCap());
            price.setText(splitItemObjects.get(position).getPrice());
            volume.setText(splitItemObjects.get(position).getVolume());
        }



        return rowView;
    }
}

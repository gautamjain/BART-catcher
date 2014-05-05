package com.bartproject.app;

import com.bartproject.app.model.FavoriteStation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoritesAdapter extends ArrayAdapter<FavoriteStation>{

    public FavoritesAdapter(Context context, ArrayList<FavoriteStation> stations) {
        super(context, 0, stations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.favorite_station_item, null);
        }

        TextView tvFavoriteName = (TextView) view.findViewById(R.id.tvFavoriteName);
        TextView tvStationName = (TextView) view.findViewById(R.id.tvStationName);

        FavoriteStation favoriteStation = getItem(position);

        tvFavoriteName.setText(favoriteStation.getLabel());
        tvStationName.setText(favoriteStation.getStation().getName());

        return view;
    }
}

package com.bartproject.app.activity;

import com.bartproject.app.FavoritesAdapter;
import com.bartproject.app.R;
import com.bartproject.app.model.FavoriteStation;
import com.bartproject.app.model.Station;
import com.bartproject.app.util.FavoritesUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteStationActivity extends Activity {

    private static final int SELECT_STATION_REQUEST_CODE = 1;
    private static final int EDIT_FAVORITE_STATION_REQUEST_CODE = 2;
    ListView lvFavorites;
    Button btnAddFavorite;
    FavoritesAdapter adapter;
    private int favoritePosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_station);

        btnAddFavorite = (Button) findViewById(R.id.add_favorite_station_button);
        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditFavorite(-1);
            }
        });


        lvFavorites = (ListView) findViewById(R.id.lvFavorites);
        adapter = new FavoritesAdapter(this, new ArrayList<FavoriteStation>(0));
        lvFavorites.setAdapter(adapter);

        lvFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addEditFavorite(position);
            }
        });
    }

    public void addEditFavorite(int position) {
        Intent intent = new Intent(FavoriteStationActivity.this, SelectStationActivity.class);
        intent.putExtra(SelectStationActivity.EXTRA_TITLE, "Select a favorite station:");
        favoritePosition = position;
        startActivityForResult(intent, SELECT_STATION_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_STATION_REQUEST_CODE) {
            Station selectedStation = (Station) data.getSerializableExtra(SelectStationActivity.ITEM_STATION);

            Intent intent = new Intent(this, EditFavoriteStationActivity.class);
            intent.putExtra(EditFavoriteStationActivity.EXTRA_STATION, selectedStation);
            startActivityForResult(intent, EDIT_FAVORITE_STATION_REQUEST_CODE);

        } else if (resultCode == Activity.RESULT_OK && requestCode == EDIT_FAVORITE_STATION_REQUEST_CODE) {
            FavoriteStation station = (FavoriteStation) data.getSerializableExtra(EditFavoriteStationActivity.ITEM_FAVORITE_STATION);

            List<FavoriteStation> favoriteStations = FavoritesUtil.readFavorites(this);

            if (favoritePosition < 0)
                favoriteStations.add(station);
            else {
                favoriteStations.remove(favoritePosition);
                favoriteStations.add(favoritePosition, station);
            }

            FavoritesUtil.saveFavorites(this, favoriteStations);

            // Update the current list
            adapter.clear();
            adapter.addAll(favoriteStations);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // Update adapter with favorites
        adapter.clear();
        adapter.addAll(FavoritesUtil.readFavorites(this));
    }

}

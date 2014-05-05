package com.bartproject.app.activity;

import com.bartproject.app.FavoritesAdapter;
import com.bartproject.app.R;
import com.bartproject.app.SwipeDismissListViewTouchListener;
import com.bartproject.app.model.FavoriteStation;
import com.bartproject.app.model.Station;
import com.bartproject.app.util.FavoritesUtil;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteStationActivity extends ListActivity {

    private static final int SELECT_STATION_REQUEST_CODE = 1;
    private static final int EDIT_FAVORITE_STATION_REQUEST_CODE = 2;
    Button btnAddFavorite;
    FavoritesAdapter mAdapter;
    private int favoritePosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_station);

        // Hide action bar logo
        getActionBar().setDisplayShowHomeEnabled(false);

        btnAddFavorite = (Button) findViewById(R.id.add_favorite_station_button);
        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditFavorite(-1);
            }
        });


        mAdapter = new FavoritesAdapter(this, new ArrayList<FavoriteStation>(0));
        setListAdapter(mAdapter);

        /**
         * SETUP FOR SWIPEABLE LIST ITEMS:
         * copied from
         * https://github.com/romannurik/Android-SwipeToDismiss/
         */
        ListView listView = getListView();
        // Create a ListView-specific touch listener. ListViews are given special treatment because
        // by default they handle touches for their list items... i.e. they're in charge of drawing
        // the pressed state (the list selector), handling list item clicks, etc.
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mAdapter.remove(mAdapter.getItem(position));
                                    Log.e("DISMISSED", "position = " + position);

                                    // Load saved favorites, delete the specified position, and save favorites to file
                                    List<FavoriteStation> favoriteStations = FavoritesUtil.readFavorites(FavoriteStationActivity.this);
                                    favoriteStations.remove(position);
                                    FavoritesUtil.saveFavorites(FavoriteStationActivity.this, favoriteStations);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(touchListener.makeScrollListener());
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        addEditFavorite(position);
    }

    public void addEditFavorite(int position) {
        Intent intent = new Intent(FavoriteStationActivity.this, SelectStationActivity.class);
        intent.putExtra(SelectStationActivity.EXTRA_TITLE, "Select your favorite station");
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
            mAdapter.clear();
            mAdapter.addAll(favoriteStations);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // Update mAdapter with favorites
        mAdapter.clear();
        mAdapter.addAll(FavoritesUtil.readFavorites(this));
    }

}

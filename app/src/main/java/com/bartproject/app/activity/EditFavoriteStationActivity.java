package com.bartproject.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bartproject.app.R;
import com.bartproject.app.model.FavoriteStation;
import com.bartproject.app.model.Station;


public class EditFavoriteStationActivity extends Activity
{
    public static final String EXTRA_STATION = "EXTRA_STATION";
    public static final String ITEM_FAVORITE_STATION = "ITEM_FAVORITE_STATION";
    private TextView tvStationName;
    private EditText etFavoriteName;
    private Button btnSave;
    private Station station;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_favorite_station);

        // Hide action bar logo
        getActionBar().setDisplayShowHomeEnabled(false);

        station = (Station) getIntent().getSerializableExtra(EXTRA_STATION);

        tvStationName = (TextView) findViewById(R.id.tvStationName);
        tvStationName.setText(station.getName());

        etFavoriteName = (EditText) findViewById(R.id.etFavoriteName);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favoriteName = etFavoriteName.getText().toString();

                //.equals didn't work for editText
                //Check if empty and not let the user continue
                if (favoriteName.matches(""))
                {
                    Toast.makeText(getBaseContext(),"Enter Favorite Name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    FavoriteStation favoriteStation = new FavoriteStation(favoriteName, station);
                    Intent data = new Intent();
                    data.putExtra(ITEM_FAVORITE_STATION, favoriteStation);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

}

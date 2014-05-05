package com.bartproject.app;

import com.bartproject.app.model.FavoriteStation;
import com.bartproject.app.model.Station;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EditFavoriteStationActivity extends Activity
{
    public static final String EXTRA_STATION = "EXTRA_STATION";
    public static final String ITEM_FAVORITE_STATION = "ITEM_FAVORITE_STATION";
    private TextView tvStationName;
    private EditText etFavoriteName;
    private Button btnSave;
    private Station station;

    //    public findViewById(R.id.activity_select_favorite_station).setOnClickListener(View.OnClickListener);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_favorite_station);

        station = (Station) getIntent().getSerializableExtra(EXTRA_STATION);

        tvStationName = (TextView) findViewById(R.id.tvStationName);
        tvStationName.setText(station.getName());

        etFavoriteName = (EditText) findViewById(R.id.etFavoriteName);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favoriteName = etFavoriteName.getText().toString();

                FavoriteStation favoriteStation = new FavoriteStation(favoriteName, station);
                // TODO: Check if empty and not let the user continue

                Intent data = new Intent();

                data.putExtra(ITEM_FAVORITE_STATION, favoriteStation);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_favorite_station, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

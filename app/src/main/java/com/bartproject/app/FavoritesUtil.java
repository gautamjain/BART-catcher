package com.bartproject.app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.bartproject.app.model.FavoriteStation;
import com.bartproject.app.model.Station;

import org.apache.commons.io.FileUtils;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesUtil {

    public static ArrayList<FavoriteStation> readFavorites(Context context) {
        // Initialize GSON
        Gson gson = new Gson();
        Type listType = new TypeToken<List<FavoriteStation>>() {}.getType();

        // Get file
        File filesDir = context.getFilesDir();
        File file = new File(filesDir, "favorites.txt");

        ArrayList<FavoriteStation> favoriteStations;

        // Attempt to read file & convert from JSON to list
        try {
            favoriteStations = gson.fromJson(FileUtils.readFileToString(file), listType);
        } catch (IOException e) {
            favoriteStations = new ArrayList<FavoriteStation>(0);
            e.printStackTrace();
        }

        // If no favorites exist, then generate some fake favorites and save for next time
        if (favoriteStations.size() == 0) {
            favoriteStations = generateAndSaveFakeStations(context);
        }

        return favoriteStations;
    }

    public static void saveFavorites(Context context, ArrayList<FavoriteStation> favoriteStations) {
        // Initialize GSON
        Gson gson = new Gson();
        Type listType = new TypeToken<List<FavoriteStation>>() {}.getType();

        // Get file
        File filesDir = context.getFilesDir();
        File file = new File(filesDir, "favorites.txt");

        // Attempt to convert to JSON and write to file
        try {
            FileUtils.writeStringToFile(file, gson.toJson(favoriteStations, listType));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<FavoriteStation> generateAndSaveFakeStations(Context context) {
        FavoriteStation a = new FavoriteStation("Work", new Station("Powell St"));
        FavoriteStation b = new FavoriteStation("Home", new Station("El Cerrito Plaza"));
        FavoriteStation c = new FavoriteStation("Airport", new Station("SFO"));
        FavoriteStation d = new FavoriteStation("Downtown", new Station("Downtown Berkeley"));

        ArrayList<FavoriteStation> stations = new ArrayList<FavoriteStation>(4);
        stations.add(a);
        stations.add(b);
        stations.add(c);
        stations.add(d);

        // Write the fake favorites to file (for next time)
        saveFavorites(context, stations);

        // Return fake favorites
        return stations;
    }

}

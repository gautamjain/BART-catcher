package com.bartproject.app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.bartproject.app.model.FavoriteStation;

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

}

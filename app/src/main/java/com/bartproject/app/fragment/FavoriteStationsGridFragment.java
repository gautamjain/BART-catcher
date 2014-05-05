package com.bartproject.app.fragment;

import com.bartproject.app.FavoritesAdapter;
import com.bartproject.app.util.FavoritesUtil;
import com.bartproject.app.R;
import com.bartproject.app.model.FavoriteStation;
import com.bartproject.app.model.Station;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * The bottom most fragment in on the main screen.  This fragment contains a GridView of the favorite destinations
 */
public class FavoriteStationsGridFragment extends Fragment {

    private OnDestinationSelectedListener mListener;

    private GridView gvFavorites;
    private FavoritesAdapter adapter;

    public FavoriteStationsGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_grid, container, false);

        gvFavorites = (GridView) view.findViewById(R.id.gvFavorites);
        adapter = new FavoritesAdapter(getActivity(), new ArrayList<FavoriteStation>(0));
        gvFavorites.setAdapter(adapter);

        gvFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                onDestinationSelected(adapter.getItem(position));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Update adapter with favorites
        adapter.clear();
        adapter.addAll(FavoritesUtil.readFavorites(getActivity()));
    }

    public void onDestinationSelected(FavoriteStation destination) {
        if (mListener != null) {
            mListener.onDestinationSelected(destination.getStation());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDestinationSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDestinationSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDestinationSelectedListener {
        public void onDestinationSelected(Station destination);
    }

}

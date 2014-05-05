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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Setup a GridView and adapter
        // Read the two preferences arrays from the SharedPreferences and load data into the adapter
        // First array should contain a list of 'favorite station labels', i.e. Home, Work, School, etc.
        // Second array should contain a list (of the same size) of corresponding 'favorite stations', i.e. Powell, Berkeley, Fremont

        // TODO: XML Layouts
        // Create a layout file for this fragment.  Should include a title, e.g. 'Where are you going?' and a GridView
        // Also need to create a layout file for the GridView's individual items. Will probably contain two TextViews.

        // TODO: Register an onItemClickListener
        // Should call onDestinationSelected
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_grid, container, false);

        gvFavorites = (GridView) view.findViewById(R.id.gvFavorites);
        adapter = new FavoritesAdapter(getActivity(), new ArrayList<FavoriteStation>(0));
        gvFavorites.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Update adapter with favorites
        adapter.clear();
        adapter.addAll(FavoritesUtil.readFavorites(getActivity()));
    }

    public void onDestinationSelected(Station destination) {
        // TODO: Report the selected destination back to the parent Activity (i.e. mListener).  May have to change arguments.

        if (mListener != null) {
            mListener.onDestinationSelected(destination);
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

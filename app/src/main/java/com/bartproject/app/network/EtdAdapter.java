package com.bartproject.app.network;

import com.bartproject.app.R;
import com.bartproject.app.model.Estimate;
import com.bartproject.app.model.Etd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anu on 4/26/14.
 * This adapter inflates station_item
 */
public class EtdAdapter extends ArrayAdapter<Etd>
{
    private ImageView imgColorBarIcon;
    private TextView tvDestTrainName;
    private TextView tvArrivalTime;

    public EtdAdapter(Context context, List<Etd> etdListItem)
    {
        super(context, 0, etdListItem);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // convertview resuses the list memory as we scroll down.
        View view = convertView;

        //create the view from the layout if null
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_nearest_station_item, null);
        }

        Etd etd = getItem(position);

        // i'm using the ArrayList instead of List because by using the arrayList
        // I can implement methods in both List interface as well as ArrayList class
        // In other classes we have declared as List.to maintain consistency - use List
        //List<Estimate> estimates = new ArrayList();
        //estimates = etdItem.getEstimatesList(); // this is to get the color
        List <Estimate> estimates = etd.getEstimatesList();

        //TODO: get the icon for the coloricon and change the color
        // find out how to change the color of the image estimates.get(0).gethexColor()
        imgColorBarIcon= (ImageView) view.findViewById(R.id.imgColorIcon);
        imgColorBarIcon.setBackgroundColor(estimates.get(0).getColor());

        tvDestTrainName = (TextView) view.findViewById(R.id.tvTrainDestNameItem);
        tvDestTrainName.setText(etd.getDestinationName());

        tvArrivalTime = (TextView) view.findViewById(R.id.tvArrivalTimeItem);

        String firstData = estimates.get(0).getMinutes();
        if (!firstData.equals("Leaving")) {
            firstData = firstData + " minutes";
        }

        tvArrivalTime.setText(firstData);

        return view;


    }

}

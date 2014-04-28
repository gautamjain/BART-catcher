package com.bartproject.app.network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartproject.app.R;
import com.bartproject.app.model.Estimate;
import com.bartproject.app.model.Etd;

import java.util.List;

/**
 * Created by Anu on 4/26/14.
 * This adapter inflates station_item
 */
public class EtdAdapter extends ArrayAdapter<Etd>
{

    private ImageView imgTrainIcon;
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

        Etd etdItem = getItem(position);

        //TODO: change it to Train Icon in the drawable
        imgTrainIcon = (ImageView) view.findViewById(R.id.imgTrainIcon);
        imgTrainIcon.setImageResource(R.drawable.ic_launcher);


        // i'm using the ArrayList instead of List because by using the arrayList
        // I can implement methods in both List interface as well as ArrayList class
        // In other classes we have declared as List.to maintain consistency - use List
        //List<Estimate> estimates = new ArrayList();
        //estimates = etdItem.getEstimateTimeOfDep(); // this is to get the color
        List <Estimate> estimates = etdItem.getEstimateTimeOfDep();

        //TODO: get the icon for the coloricon and change the color
        // find out how to change the color of the image estimates.get(0).gethexColor()
        imgColorBarIcon= (ImageView) view.findViewById(R.id.imgColorIcon);
        imgColorBarIcon.setImageResource(R.drawable.ic_launcher);

        Toast.makeText(getContext(), "set Time and DestTrain Name ETD Adapter", Toast.LENGTH_SHORT).show();

        tvDestTrainName = (TextView) view.findViewById(R.id.tvTrainDestNameItem);
        Toast.makeText(getContext(), etdItem.getDestinationName(), Toast.LENGTH_SHORT).show();
        tvDestTrainName.setText(etdItem.getDestinationName());

        tvArrivalTime = (TextView) view.findViewById(R.id.tvArrivalTimeItem);

        String firstData = estimates.get(0).getMinutes();
        Toast.makeText(getContext(), firstData, Toast.LENGTH_SHORT).show();
        tvArrivalTime.setText(firstData);


        return view;


    }

}

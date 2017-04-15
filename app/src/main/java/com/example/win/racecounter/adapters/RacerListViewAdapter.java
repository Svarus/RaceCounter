package com.example.win.racecounter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.win.racecounter.R;
import com.example.win.racecounter.models.Racer;

import java.util.ArrayList;

public class RacerListViewAdapter extends ArrayAdapter<Racer> {
    public RacerListViewAdapter(Context context, ArrayList<Racer> racers) {
        super(context, 0, racers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Racer racer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_racer, parent, false);
        }

        // Lookup view for data population
        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        TextView tvSurname = (TextView) convertView.findViewById(R.id.tvSurname);
        TextView tvNname = (TextView) convertView.findViewById(R.id.tvName);

        // Populate the data into the template view using the data object
        tvId.setText(String.valueOf(racer.getId()));
        tvSurname.setText(racer.getSurname());
        tvNname.setText(racer.getName());

        // Return the completed view to render on screen
        return convertView;
    }

}

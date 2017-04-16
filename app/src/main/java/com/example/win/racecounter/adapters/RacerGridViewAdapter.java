package com.example.win.racecounter.adapters;

import android.content.Context;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.win.racecounter.R;
import com.example.win.racecounter.activities.MainActivity;
import com.example.win.racecounter.handlers.RacersHandler;
import com.example.win.racecounter.handlers.TRC;
import com.example.win.racecounter.models.Racer;

import java.util.ArrayList;
import java.util.Collections;

public class RacerGridViewAdapter extends ArrayAdapter<Racer> {
    public RacerGridViewAdapter(Context context, ArrayList<Racer> racers) {
        super(context, 0, racers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Racer racer = getItem(position);
        final String id = String.valueOf(racer.getId());
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_racer_gv, parent, false);
        }

        // Lookup view for data population
        final Button btButton = (Button) convertView.findViewById(R.id.racerButton);

        // Cache row position inside the button using `setTag`
        btButton.setTag(position);

        //btButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        btButton.setAllCaps(false);
        btButton.setText(RacersHandler.spanInfoRacer(racer));
        //btButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        btButton.setEnabled(MainActivity.raceWasStarted);

        // Attach the click event handler
        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();

                // Access the row position here to get the correct data item
                Racer racer = getItem(position);

/*                Snackbar.make(view, racer.getSurname(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                // Do what you want here...
                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);
                RacersHandler.racerPassedFinishLine(racer);
                btButton.setText(RacersHandler.spanInfoRacer(racer));

                Collections.sort(MainActivity.arrayOfRacersSorted, new TRC());
                RacersHandler.PopulateRacersToTable(getContext(), MainActivity.tableView, MainActivity.arrayOfRacersSorted);

            }

        });

        // Return the completed view
        return convertView;
    }
}
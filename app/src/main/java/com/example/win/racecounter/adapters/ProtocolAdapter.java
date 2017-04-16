package com.example.win.racecounter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win.racecounter.R;
import com.example.win.racecounter.activities.MainActivity;
import com.example.win.racecounter.handlers.DialogDeleteRacerLap;
import com.example.win.racecounter.handlers.RacersHandler;
import com.example.win.racecounter.handlers.TRC;
import com.example.win.racecounter.models.Racer;
import com.example.win.racecounter.models.RacerProtocol;

import java.util.ArrayList;
import java.util.Collections;

public class ProtocolAdapter extends ArrayAdapter<RacerProtocol> {
    private static final int POWER = 9;
    private FragmentActivity myContext;
    private Activity activity;

    public ProtocolAdapter(Context context, ArrayList<RacerProtocol> racers, Activity activity) {
        super(context, 0, racers);
        this.myContext = (FragmentActivity) activity;
        this.activity = activity;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //for reversed view
        final int revPosition = MainActivity.arrayOfRacersProtocol.size() - position - 1;

        //RacerProtocol racerProtocol = getItem(position);
        final RacerProtocol racerProtocol = getItem(revPosition);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_protocol, parent, false);
        }

        // Lookup view for data population
        TextView tvProtocol = (TextView) convertView.findViewById(R.id.tvProtocol);
        tvProtocol.setText(racerProtocol.toStringSpannable());
        //tvProtocol.setTag(position);
        tvProtocol.setTag(revPosition);

        tvProtocol.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                final int position = (Integer) view.getTag();
                final RacerProtocol racerProtocol = getItem(position);

                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(getContext().getString(R.string.dialog_delete_racer_lap) + racerProtocol.toString() + "?")
                        .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Operate with arrayOfRacers - delete lap
                                int index = racerProtocol.getLaps() - 1;
                                Racer racer = null;
                                for (int i = 0; i < MainActivity.arrayOfRacers.size(); i++){
                                    racer = MainActivity.arrayOfRacers.get(i);
                                    if (racer.getId() == racerProtocol.getId() && racer.getSurname()== racerProtocol.getSurname()){
                                        //if (racer.getSurname()!= racerProtocol.getSurname()) {continue;}
                                        long time = racer.getLapN().get(index);
                                        if (racer.getLapN().size() > index + 1){
                                            time+= racer.getLapN().get(index + 1);
                                            MainActivity.arrayOfRacers.get(i).changeLapN(index + 1, time);
                                        }
                                        else {
                                            long currentTime = racer.getCurrentTime();
                                            currentTime-= time;
                                            MainActivity.arrayOfRacers.get(i).removeLapN(index);
                                            MainActivity.arrayOfRacers.get(i).setCurrentTime(currentTime);
                                        }

                                        MainActivity.arrayOfRacers.get(i).setLaps(racer.getLaps() - 1);

                                        RacerProtocol racerProtocolLoop = null;
                                        for (int j = position + 1; j < MainActivity.arrayOfRacersProtocol.size(); j++){
                                            racerProtocolLoop = MainActivity.arrayOfRacersProtocol.get(j);
                                            if (racerProtocolLoop.getId() == racerProtocol.getId() && racerProtocolLoop.getSurname()== racerProtocol.getSurname()){
                                                racerProtocolLoop.setLaps(racerProtocolLoop.getLaps() - 1);
                                            }
                                        }
                                        MainActivity.arrayOfRacersProtocol.remove(position);

                                        MainActivity.protocolAdapter.notifyDataSetChanged();
                                        MainActivity.racersAdapterGv.notifyDataSetChanged();
                                        RacersHandler.PopulateRacersToTable(getContext(), MainActivity.tableView, MainActivity.arrayOfRacersSorted);

                                        break;
                                    }
                                }

                            }
                        })
                        .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.create();
                builder.show();

                return false;
            }
        });

        // Return the completed view
        return convertView;
    }
}
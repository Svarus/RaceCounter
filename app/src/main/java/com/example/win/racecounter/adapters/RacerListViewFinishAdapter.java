package com.example.win.racecounter.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.win.racecounter.R;
import com.example.win.racecounter.activities.MainActivity;
import com.example.win.racecounter.handlers.RacersHandler;
import com.example.win.racecounter.handlers.TimeHelper;
import com.example.win.racecounter.models.Racer;

import java.sql.Time;
import java.util.ArrayList;

public class RacerListViewFinishAdapter extends ArrayAdapter<Racer> {
    private Context context;

    private int padding = 10;
    private int numColumns = 6;
    private int maxNameWidth = 0;
    private int maxLaps = 0;

    public RacerListViewFinishAdapter(Context context, ArrayList<Racer> racers) {
        super(context, 0, racers);
        this.context = context;

        TextView tvTemp = new TextView(context);
        tvTemp.setText(RacersHandler.getLongestName(MainActivity.arrayOfRacers).get(0).toString());
        tvTemp.measure(0, 0);
        maxNameWidth = tvTemp.getMeasuredWidth();
        tvTemp = null;

        maxLaps = (int) RacersHandler.getLongestName(MainActivity.arrayOfRacers).get(1);
        numColumns = numColumns + maxLaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Racer racer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_racer_finish_gl, parent, false);

            /*TextView tvTemp = new TextView(context);
            tvTemp.setText(RacersHandler.getLongestName(MainActivity.arrayOfRacers));
            tvTemp.measure(0, 0);
            int maxNameWidth = tvTemp.getMeasuredWidth();
            tvTemp = null;*/

            GridLayout table = (GridLayout) convertView.findViewById(R.id.glFinishTable);
            table.setColumnCount(numColumns);

            TextView tvId = new TextView(context);
            tvId.setPadding(padding, padding, 0, 0);
            tvId.setText("# " + racer.getId());
            table.addView(tvId);

            TextView tvName = new TextView(context);
            tvName.setPadding(padding, padding, 0, 0);
            tvName.setText(racer.getSurname() + " " + racer.getName());
            tvName.setWidth(maxNameWidth + padding);
            table.addView(tvName);

            TextView tvLaps = new TextView(context);
            tvLaps.setPadding(padding, padding, 0, 0);
            tvLaps.setText(String.valueOf(racer.getLaps()));
            table.addView(tvLaps);

            TextView tvTime = new TextView(context);
            tvTime.setPadding(padding, padding, 0, 0);
            tvTime.setText( (new TimeHelper(racer.getCurrentTime() - MainActivity.tickStart)).toString()) ;
            table.addView(tvTime);

            String lapN = "";
            for (int i = 0; i< maxLaps; i++){
                TextView tvLapN = new TextView(context);
                tvLapN.setPadding(padding, padding, 0, 0);
                try {
                    lapN = (new TimeHelper(racer.getLapN().get(i))).toString();
                    //lapN = racer.getLapN().get(i).toString();
                } catch (Exception e){
                    lapN = "";
                }

                tvLapN.setText(lapN);
                table.addView(tvLapN);
            }

        }

        return  convertView;
    }
}

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Racer racer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_racer_finish_lv, parent, false);

            TableLayout table  = (TableLayout) convertView.findViewById(R.id.tlFinishTable);

            TableRow tableRow = new TableRow(context);
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            //params.span = 6;

            // labels column
            TextView tvId = new TextView(context);
            tvId.setText("# " + racer.getId());
            tvId.setGravity(Gravity.LEFT);
            //tvId.setTypeface(Typeface.DEFAULT_BOLD);
            //tvId.setLayoutParams(params);
            //tvId.setGravity(Gravity.LEFT);
            //tvId.setPadding(0,0,10,0);
            tableRow.addView(tvId);

            // day 1 column
            TextView tvName = new TextView(context);
            tvName.setText(racer.getSurname() + " " + racer.getName());
            //tvName.setTypeface(Typeface.SERIF, Typeface.BOLD);
            tvName.setGravity(Gravity.LEFT);
            //tvName.setGravity(Gravity.LEFT);
            //tvName.setPadding(0,0,10,0);
            tableRow.addView(tvName);

*//*            TextView tvLaps = new TextView(context);
            tvLaps.setText("laps");
            tvLaps.setGravity(Gravity.LEFT);
            //tvLaps.setLayoutParams(params);
            //tvLaps.setGravity(Gravity.LEFT);
            //tvLaps.setPadding(0,0,10,0);
            tableRow.addView(tvLaps);*//*

            table.addView(tableRow);


            *//*TableRow tableRow = new TableRow(context);
            //tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.span = 6;

            TextView tvId = new TextView(context);
            tvId.setText(String.valueOf(racer.getId()));
            tvId.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            //tableRow.addView(tvId);
            tableRow.addView(tvId, 0, params);*//*

        }

        return convertView;
    }*/

// Lookup view for data population
        /*TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        TextView tvSurname = (TextView) convertView.findViewById(R.id.tvSurname);
        TextView tvNname = (TextView) convertView.findViewById(R.id.tvName);*/

// Populate the data into the template view using the data object
        /*tvId.setText(String.valueOf(racer.getId()));
        tvSurname.setText(racer.getSurname());
        tvNname.setText(racer.getName());*/

/*        TableLayout tableLayout = (TableLayout) convertView.findViewById(R.id.tlFinishTable);

        //loop
        TableRow tableRow = new TableRow(context);
        //TableRow tableRow = (TableRow) convertView.findViewById(R.id.trFinishLine);

        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView tvId = new TextView(context);
        //TextView tvId = (TextView) convertView.findViewById(R.id.tvFin);
        tvId.setText(String.valueOf(racer.getId()));
        tvId.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.addView(tvId);
        //edn loop

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));*/

// Return the completed view to render on screen
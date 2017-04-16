package com.example.win.racecounter.handlers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.win.racecounter.R;
import com.example.win.racecounter.activities.MainActivity;
import com.example.win.racecounter.models.Racer;
import com.example.win.racecounter.models.RacerProtocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.os.SystemClock.elapsedRealtime;

public class RacersHandler {

    public static void racerPassedFinishLine(Racer racer){
        long tickNow = elapsedRealtime();
        long timeDifference;
        long timeTotalDifference = tickNow - MainActivity.tickStart;

        //int size = racer.getLapN().size();
        //if (racer.getLapN() == null) {
        //if(size == 0){
        if (racer.getLapN().isEmpty()) {
            timeDifference =  tickNow - MainActivity.tickStart;
        } else {
            //timeDifference = tickNow - racer.getCurrentTime();
            timeDifference = timeTotalDifference - racer.getCurrentTime();
        }

        racer.setCurrentTime(timeTotalDifference);
        racer.addLapN(timeDifference);
        racer.setLaps(racer.getLaps() + 1);

        //Protocol
        RacerProtocol racerProtocol = new RacerProtocol(
                racer.getId(),
                racer.getLaps(),
                racer.getName(),
                racer.getSurname(),
                racer.getLapN().get(racer.getLapN().size() - 1),
                racer.getCurrentTime());

        MainActivity.arrayOfRacersProtocol.add(racerProtocol);
        MainActivity.protocolAdapter.notifyDataSetChanged();
    }

    public static SpannableString spanInfoRacer(Racer racer){
        String text = racer.getId() + " " + racer.getLaps();
        SpannableString span = new SpannableString(text);
        int startPos = String.valueOf(racer.getId()).length() + 1;
        int endPos = startPos + String.valueOf(racer.getLaps()).length();
        //span.setSpan(new SubscriptSpan(), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(0.4f), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span;
    }

    public static ArrayList getLongestName(ArrayList<Racer> arrayOfRacers){
        String str= "";
        int maxLen = 0;
        int currentLength = 0;
        int maxLaps = 0;
        int currentLaps = 0;

        for(Racer racer: arrayOfRacers){
            currentLength = (racer.getName() + racer.getSurname()).length();
            if (currentLength > maxLen){
                str = racer.getSurname() + " " + racer.getName();
                maxLen = currentLength;
            }
            currentLaps = racer.getLaps();
            if (currentLaps > maxLaps){
                maxLaps = currentLaps;
            }
        }

        ArrayList arrayList = new ArrayList(2);
        arrayList.add(0, str);
        arrayList.add(1, maxLaps);

        return arrayList;
    }

    class IdRacerComparator implements Comparator<Racer> {
        @Override
        public int compare(@NonNull Racer o1, @NonNull Racer o2) {
            return o1.getId() < o2.getId() ? -1 : ((o1.getId() == o2.getId()) ? 0 : 1);
        }
    }

    class TimeRacerComparator implements Comparator<Racer> {
        @Override
        public int compare(@NonNull Racer o1, @NonNull Racer o2) {
            return o1.getTime() < o2.getTime() ? -1 : ((o1.getTime() == o2.getTime()) ? 0 : 1);
        }
    }

    private static View drawVericalLine(Context context, View view){
        ImageView dividerView = new ImageView(context);
        dividerView.setImageResource(R.drawable.divider);

        return dividerView;
    }

    public static void PopulateRacersToTable(Context context, View view, ArrayList<Racer> arrayOfRacersSorted){
        TableLayout tlFinishTable = (TableLayout) view.findViewById(R.id.tlFinishTable);

        //clear table contents   TODO optimization to reuse existing table rows
        tlFinishTable.removeAllViews();

        if (!arrayOfRacersSorted.isEmpty()) {
            //sort according to racer postition
            Collections.sort(arrayOfRacersSorted, new TRC());

            View horizontalDivider = new View(context);
            horizontalDivider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 1));
            horizontalDivider.setBackgroundColor(Color.rgb(51, 51, 51));

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

            TableRow tableRow = new TableRow(context);
            Typeface tf = Typeface.DEFAULT_BOLD;

            TextView tvPos = new TextView(context);
            tvPos.setGravity(Gravity.RIGHT);
            tvPos.setText(R.string.finish_position_label);
            tvPos.setTypeface(tf);
            tvPos.setPadding(10, 0, 10, 0);
            tableRow.addView(tvPos);
            tableRow.addView(drawVericalLine(context, view));

            TextView tvId = new TextView(context);
            tvId.setGravity(Gravity.RIGHT);
            tvId.setText(R.string.finish_id_label);
            tvId.setTypeface(tf);
            tvId.setPadding(10, 0, 10, 0);
            tableRow.addView(tvId);
            tableRow.addView(drawVericalLine(context, view));

            TextView tvName = new TextView(context);
            tvName.setText(R.string.finish_name_label);
            tvName.setTypeface(tf);
            tvName.setGravity(Gravity.LEFT);
            tvName.setPadding(10, 0, 10, 0);
            tableRow.addView(tvName);
            tableRow.addView(drawVericalLine(context, view));

            TextView tvLaps = new TextView(context);
            tvLaps.setText(R.string.finish_laps_label);
            tvLaps.setTypeface(tf);
            tvLaps.setGravity(Gravity.CENTER_HORIZONTAL);
            tvLaps.setPadding(10, 0, 10, 0);
            tableRow.addView(tvLaps);
            tableRow.addView(drawVericalLine(context, view));

            TextView tvTime = new TextView(context);
            tvTime.setText(R.string.finish_time_label);
            tvTime.setTypeface(tf);
            tvTime.setGravity(Gravity.CENTER_HORIZONTAL);
            tvTime.setPadding(10, 0, 10, 0);
            tableRow.addView(tvTime);
            tableRow.addView(drawVericalLine(context, view));

            //adding lapsN
            int maxLaps = 0;
            for (Racer racer : arrayOfRacersSorted) {
                maxLaps = racer.getLaps() > maxLaps ? racer.getLaps() : maxLaps;
            }
            for (int lap = 1; lap <= maxLaps; lap++) {

                tvTime = new TextView(context);
                tvTime.setText(context.getString(R.string.finish_lapN_label) + String.valueOf(lap));
                tvTime.setTypeface(tf);
                tvTime.setGravity(Gravity.CENTER_HORIZONTAL);
                tvTime.setPadding(10, 0, 10, 0);
                tableRow.addView(tvTime);
                tableRow.addView(drawVericalLine(context, view));
            }

            tlFinishTable.addView(tableRow);
            tlFinishTable.addView(horizontalDivider);

            int position = 1;
            for (Racer racer : arrayOfRacersSorted) {

                tableRow = new TableRow(context);

                tvPos = new TextView(context);
                tvPos.setGravity(Gravity.RIGHT);
                tvPos.setText(String.valueOf(position));
                tvPos.setPadding(10, 0, 10, 0);
                tableRow.addView(tvPos);
                tableRow.addView(drawVericalLine(context, view));

                tvId = new TextView(context);
                tvId.setGravity(Gravity.RIGHT);
                tvId.setText(String.valueOf(racer.getId()));
                tvId.setPadding(10, 0, 10, 0);
                tableRow.addView(tvId);
                tableRow.addView(drawVericalLine(context, view));

                tvName = new TextView(context);
                tvName.setText(racer.getSurname() + " " + racer.getName());
                //tvName.setTypeface(Typeface.SERIF, Typeface.BOLD);
                tvName.setGravity(Gravity.LEFT);
                tvName.setPadding(10, 0, 10, 0);
                tableRow.addView(tvName);
                tableRow.addView(drawVericalLine(context, view));

                tvLaps = new TextView(context);
                int laps = racer.getLaps();
                String lapsString = laps > 0 ? String.valueOf(laps) : "";
                tvLaps.setText(lapsString);
                tvLaps.setGravity(Gravity.CENTER_HORIZONTAL);
                tvLaps.setPadding(10, 0, 10, 0);
                tableRow.addView(tvLaps);
                tableRow.addView(drawVericalLine(context, view));

                tvTime = new TextView(context);
                long time = racer.getTime();
                SpannableString timeString = time > 0 ? new TimeHelper(time).toStringSpannable() : new SpannableString("");
                //tvTime.setText(new TimeHelper(racer.getTime()).toStringSpannable());
                tvTime.setText(timeString);
                tvTime.setGravity(Gravity.RIGHT);
                tvTime.setPadding(10, 0, 10, 0);
                tableRow.addView(tvTime);
                tableRow.addView(drawVericalLine(context, view));

                for (int lap = 0; lap < racer.getLaps(); lap++) {

                    tvTime = new TextView(context);
                    tvTime.setText(new TimeHelper(racer.getLapN().get(lap)).toStringSpannable());
                    tvTime.setGravity(Gravity.RIGHT);
                    tvTime.setPadding(10, 0, 10, 0);
                    tableRow.addView(tvTime);
                    tableRow.addView(drawVericalLine(context, view));
                }

                tlFinishTable.addView(tableRow);
                ++position;

                horizontalDivider = new View(context);
                horizontalDivider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 1));
                horizontalDivider.setBackgroundColor(Color.rgb(51, 51, 51));
                tlFinishTable.addView(horizontalDivider);
            }
        }
    }

    public static void restartRace(){
        for (Racer racer : MainActivity.arrayOfRacers){
            racer.setCurrentTime(0);
            racer.setLaps(0);
            racer.setLapNEmpty();
        }
        Collections.sort(MainActivity.arrayOfRacers, new IdComparator());
        MainActivity.arrayOfRacersProtocol.clear();
    }

/*    public static Racer setRacerJson (JSONObject object){
        //Racer racer = new Racer();
        try {
            racer.setId(object.getInt("id"));
            racer.setName(object.getString("name"));
            racer.setSurname(object.getString("surname"));
            racer.setCurrentTime(object.getLong("currentTime"));
            racer.setLaps(object.getInt("laps"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Racer racer = new Racer(object.getInt("id"),)
        return racer;
    }*/

/*        // Factory method to convert an array of JSON objects into a list of objects
    // User.fromJson(jsonArray);
    public static ArrayList<Racer> fromJson(JSONArray jsonObjects) {
        ArrayList<Racer> racers = new ArrayList<Racer>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                racers.add(new Racer(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return racers;
    }*/
}



   /* TextView tvName = new TextView(context);
        tvName.setText(arrayOfRacersSorted.get(0).getSurname() + " " + arrayOfRacersSorted.get(0).getName());
                //tvName.setTypeface(Typeface.SERIF, Typeface.BOLD);
                tvName.setGravity(Gravity.LEFT);
                tvName.setPadding(0,0,10,0);
                tableRow.addView(tvName);

                TextView tvLaps = new TextView(context);
                tvLaps.setText(arrayOfRacersSorted.get(0).getLaps());
                tvLaps.setGravity(Gravity.LEFT);
                tvLaps.setPadding(0,0,10,0);
                //tvLaps.setLayoutParams(params);
                tableRow.addView(tvLaps);

                tlFinishTable.addView(tableRow);*/
package com.example.win.racecounter.models;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;

import com.example.win.racecounter.handlers.TimeHelper;

public class RacerProtocol {
    private int id;
    private int category;
    private int placeInCategory;
    private int laps;
    private String nick;
    private String name;
    private String surname;
    private long lapN;
    private long currentTime;

    public RacerProtocol(int id, int laps, String name, String surname, long lapN, long currentTime) {
        this.id = id;
        this.laps = laps;
        this.name = name;
        this.surname = surname;
        this.lapN = lapN;
        this.currentTime = currentTime;
    }

    public SpannableString toStringSpannable() {
        String str = this.toString();
        SpannableString span = new SpannableString(str);
        int startPos = 0;
        int endPos = 3;
        span.setSpan(new StyleSpan(Typeface.BOLD), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        startPos = str.indexOf(this.surname);
        endPos = startPos + this.surname.length();
        span.setSpan(new RelativeSizeSpan(0.75f), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span;
    }

    @Override
    public String toString(){
        TimeHelper timeHelper = new TimeHelper(currentTime);
        //return "ID: " + String.format("%03d", id) + " | " + timeHelper.toString() + " " + surname;
        return String.format("%03d |%d| %s %s", id, laps, timeHelper.toString(), surname);
    }

    public long getLapN() {
        return lapN;
    }

    public int getId() {
        return id;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public String getSurname() {
        return surname;
    }
}

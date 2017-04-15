package com.example.win.racecounter.handlers;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;

import com.example.win.racecounter.R;

public class TimeHelper {
    private long totalMilliSeconds;
    private int hours;
    private int minutes;
    private int seconds;
    private int milliSeconds;

    public TimeHelper(long totalMilliSeconds){
        totalMilliSeconds = totalMilliSeconds;

        hours = (int) ((totalMilliSeconds / (1000*60*60)) % 24);
        minutes = (int)((totalMilliSeconds / (1000*60)) % 60);
        seconds = (int) Math.round(totalMilliSeconds / 1000) % 60;
        milliSeconds = (int) Math.round(Math.round(totalMilliSeconds / 100) % 10);
    }

    public void setTotalMilliSeconds(long milliSeconds){
        totalMilliSeconds = milliSeconds;
    }
    public int getHours(){
        return hours;
    }
    public int getMinutes(){
        return minutes;
    }

    public int getSeconds(){
        return seconds;
    }
    public int getMilliseconds(){
        return milliSeconds;
    }

    public SpannableString toStringSpannable(){
        String str = this.toString();

        /*str+= hours > 0 ? String.format("%d", hours) + ":" : "";
        str+= minutes > 0 ? String.format("%02d", minutes) + ":" : String.format("%02d:", 0);
        str+= seconds > 0 ? String.format("%02d,%01d", seconds, milliSeconds) : String.format("%02d", 0);*/

        SpannableString span = new SpannableString(str);
        int startPos = str.length() - 1;
        int endPos = str.length();
        span.setSpan(new RelativeSizeSpan(0.75f), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span;
    }

    @Override
    public String toString(){

        String str = "";
        str+= hours > 0 ? String.format("%d", hours) + ":" : "";
        str+= minutes > 0 ? String.format("%02d", minutes) + ":" : String.format("%02d:", 0);
        str+= seconds >= 0 ? String.format("%02d,%01d", seconds, milliSeconds) : String.format("%02d", 0);

        return str;
    }

}



        /*        String str = "";
        str+= hours > 0 ? hours + "h " : "";
        str+= minutes > 0 ? minutes + "m " : "";
        str+= seconds > 0 ? seconds + "," + milliSeconds + "s" : "";*/

        /*String str = "";
        str+= hours > 0 ? hours + ":" : "";
        str+= minutes > 0 ? minutes + ":" : "";
        str+= seconds > 0 ? seconds : "";*/


/*      String str = "";
        str+= hours > 0 ? String.format("%02d", hours) + ":" : "";
        str+= minutes > 0 ? String.format("%02d", minutes) + ":" : "";
        str+= seconds > 0 ? String.format("%02d", seconds) : "";*/
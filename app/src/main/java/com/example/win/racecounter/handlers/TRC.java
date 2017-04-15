package com.example.win.racecounter.handlers;

import android.support.annotation.NonNull;

import com.example.win.racecounter.models.Racer;

import java.util.Comparator;

public class TRC implements Comparator<Racer> {
    @Override
    public int compare(@NonNull Racer o1, @NonNull Racer o2) {
        int laps1 = o1.getLaps();
        int laps2 = o2.getLaps();
        if (laps1 == 0 && laps2!= 0){
            return 1;
        }
        if (laps1!= 0 && laps2 == 0){
            return -1;
        }
        if (laps1 == 0 && laps2 == 0){
            return 0;
        }

        if (laps1 == laps2){
            return o1.getCurrentTime() < o2.getCurrentTime() ? -1 : 1;
        }
        else {
            return laps1 < laps2 ? 1 : -1;
        }
    }
}

//Returns:
//-1 o1 < o2
// 0 01 == 02
// 1 01 > 02

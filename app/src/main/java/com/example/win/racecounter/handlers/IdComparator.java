package com.example.win.racecounter.handlers;

import android.support.annotation.NonNull;

import com.example.win.racecounter.models.Racer;

import java.util.Comparator;

public class IdComparator implements Comparator<Racer> {
    @Override
    public int compare(@NonNull Racer o1, @NonNull Racer o2) {
        int id1 = o1.getId();
        int id2 = o2.getId();
        return id1 < id2 ? -1 : id1 == id2 ? 0 : 1;
    }
}

//Returns:
//-1 o1 < o2
// 0 01 == 02
// 1 01 > 02
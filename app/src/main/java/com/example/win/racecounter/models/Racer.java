package com.example.win.racecounter.models;

import android.support.annotation.NonNull;

import com.example.win.racecounter.handlers.TimeHelper;

import java.util.ArrayList;
import java.util.Comparator;

//class Racer implements Comparable<Racer> {
public class Racer {
    private int id;
    private int category;
    private int placeInCategory;
    private int laps;
    private String nick;
    private String name;
    private String surname;
    private ArrayList<Long> lapN = new ArrayList<Long>();
    private long currentTime;

    public Racer(int id, String surname, String name,  long currentTime, int laps){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.currentTime = currentTime;
        this.laps = laps;
    }

    public Racer(int id, String surname, String name,  long currentTime, int laps, ArrayList<Long> lapN){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.currentTime = currentTime;
        this.laps = laps;
        this.lapN = lapN;
    }

    public int getId(){
        return id;
    }
    public String getName(){ return  name; }
    public String getSurname(){ return  surname; }
    public ArrayList<Long> getLapN() { return lapN; }
    public long getCurrentTime() { return currentTime; }
    public int getLaps() { return laps; }
    public int getPlaceInCategory() { return placeInCategory; }
    public long getTime(){ return currentTime; }

    public void setName(String name){ this.name = name; }
    public void setSurname(String surname){ this.surname = surname; }
    public void setId(int id) { this.id = id; }
    public void setCurrentTime(long currentTime) { this.currentTime = currentTime; }
    public void setLaps(int laps) { this.laps = laps; }
    public void setLapNEmpty() { this.lapN.clear(); }

    public void addLapN(long time){
        this.lapN.add(time);
    }
    public void changeLapN(int index, long time){
        this.lapN.set(index, time);
    }
    public void removeLapN(int index) {
        lapN.remove(index);
    }

    @Override
    public String toString(){
        //return String.Format("ID: {0} | {1}h {2}m {3},{4}s", ID.ToString(), currentTime.Hours.ToString(), currentTime.Minutes.ToString(), currentTime.Seconds.ToString(), (currentTime.Milliseconds / 100).ToString());
        TimeHelper timeHelper = new TimeHelper(currentTime);
        return "ID: " + id + " | " + timeHelper.toString();
    }

}


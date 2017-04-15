package com.example.win.racecounter.handlers;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;

import com.example.win.racecounter.models.Racer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    private boolean isResults;

    public void writeJsonStream(OutputStream out, List<Racer> racers, boolean isResults) throws IOException {
        this.isResults = isResults;
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeRacersArray(writer, racers);
        writer.close();
    }

    public void writeRacersArray(JsonWriter writer, List<Racer> racers) throws IOException {
        writer.beginArray();
        for (Racer racer : racers) {
            writeRacer(writer, racer);
        }
        writer.endArray();
    }

    public void writeRacer(JsonWriter writer, Racer racer) throws IOException {
        writer.beginObject();
        writer.name("id").value(racer.getId());
        writer.name("surname").value(racer.getSurname());
        writer.name("name").value(racer.getName());

        if (isResults){
            writer.name("currentTime").value(racer.getCurrentTime());
            writer.name("laps").value(racer.getLaps());
            writer.name("placeInCategory").value(racer.getPlaceInCategory());

            if (racer.getLapN() != null) {
                writer.name("lapN");
                writeLongsArray(writer, racer.getLapN());
            } else {
                writer.name("lapN").nullValue();
            }
        }
        writer.endObject();
    }

    public void writeLongsArray(JsonWriter writer, List<Long> longs) throws IOException {
        writer.beginArray();
        for (Long value : longs) {
            writer.value(value);
        }
        writer.endArray();
    }

    public ArrayList<Racer> readJsonStream(InputStream in) throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readRacersArray(reader);
        } finally {
            reader.close();
        }
    }

    public ArrayList<Racer> readRacersArray(JsonReader reader) throws IOException {
        ArrayList<Racer> messages = new ArrayList<Racer>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readRacer(reader));
        }
        reader.endArray();
        return messages;
    }

    public Racer readRacer(JsonReader reader) throws IOException {
        int id = -1;
        String surname = "";
        String name = "";
        long currentTime = 0;
        int laps = 0;
        long positionSortTime = Long.MAX_VALUE;
        int placeInCategory = 0;
        Racer racer = null;
        //ArrayList<Long> lapN = null;
        ArrayList<Long> lapN = new ArrayList<Long>();

        reader.beginObject();
        while (reader.hasNext()) {
            String nameJson = reader.nextName();
            if (nameJson.equals("id")) {
                id = reader.nextInt();
            } else if (nameJson.equals("surname")) {
                surname = reader.nextString();
            } else if (nameJson.equals("name")) {
                name = reader.nextString();
            } else if (nameJson.equals("currentTime")) {
                currentTime = reader.nextLong();
            } else if (nameJson.equals("laps")) {
                laps = reader.nextInt();
            }
            else if (nameJson.equals("placeInCategory")) {
                placeInCategory = reader.nextInt();
            }
            else if (nameJson.equals("lapN") && reader.peek() != JsonToken.NULL) {
                lapN = readLongsArray(reader);
            } else {
                //lapN = new ArrayList<Long>();
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Racer(id, surname, name, currentTime, laps, lapN);
    }

    public ArrayList<Long> readLongsArray(JsonReader reader) throws IOException {
        ArrayList<Long> longs = new ArrayList<Long>();

        reader.beginArray();
        while (reader.hasNext()) {
            longs.add(reader.nextLong());
        }
        reader.endArray();
        return longs;
    }
}

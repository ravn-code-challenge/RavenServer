package com.company;

import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GiphyController {

    private ReadWriteLock rwlock = new ReentrantReadWriteLock();
    public GiphyList giphyList;
    JSONParser parser = new JSONParser();
    private long nextAvailableId = 0;
    public Gson gson;

    static GiphyController sGiphhyList;

    public static GiphyController getInstance() {
        if(sGiphhyList == null) sGiphhyList = new GiphyController();
        return sGiphhyList;
    }

    private GiphyController() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (
                        json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (
                        date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()))
                .create();
    }
    /**
     * Will go through entire list and find the next possible integer
     * @param
     */
    public boolean add(String json) {
        GiphyModel model = gson.fromJson(json, GiphyModel.class);
        getList(); //ensure list is not null
        model.id = nextAvailableId;
        nextAvailableId++;
        giphyList.models.add(model);
        //if write was successful
        if(writeList()) {
            //Add to list and return true
            System.out.println("Adding to giphyList: ");
            return true;
        }
        else {
            giphyList.models.remove(model);
            return false;
        }
    }

    public boolean update(String json) {
        GiphyModel model = gson.fromJson(json, GiphyModel.class);
        getList(); //ensure list is not null
        System.out.println("Updating model with id: " + model.id);
        for(int i = 0; i < giphyList.models.size(); i++) {
            if(model.id == giphyList.models.get(i).id) {
                giphyList.models.remove(i);
                giphyList.models.add(i, model);
                writeList();
                return true;
            }
        }
        return false;
    }

    /**
     * Go through list and remove that item
     * @param id
     * @return
     */
    public boolean remove(long id) {
        for(int i = 0; i < giphyList.models.size(); i++) {
            if(giphyList.models.get(i).id == id) {
                giphyList.models.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Writes list into data.json file
     * @return
     */
    public boolean writeList() {
        boolean success = true;
        try {
            rwlock.writeLock().lock();
            FileWriter file = new FileWriter("data.json");
            file.write(gson.toJson(giphyList));
            file.flush();
        }
        catch (IOException exception) {
            success = false;
        }
        finally {
            rwlock.writeLock().unlock();
        }
        return success;
    }

    public String getJsonList() {
        return gson.toJson(getList());
    }

    private GiphyList getList() {

        if(giphyList != null) {
            return giphyList;
        }

        rwlock.readLock().lock();

        try {
            System.out.println("Reading json file");
            JSONObject a = (JSONObject) parser.parse(new FileReader("data.json"));
            giphyList = gson.fromJson(a.toJSONString(), GiphyList.class);

            giphyList.models.forEach(model -> {
                if(model.id > nextAvailableId) {
                    nextAvailableId = model.id;
                }
                nextAvailableId++; //Increment the next available ID
            });

        } catch (IOException e) {
            System.out.println("IOEception: " + e);
        } catch (ParseException e) {
            System.out.println("Parse exception: " + e);
        }

        finally {
            rwlock.readLock().unlock();
        }

        return giphyList;
    }


}

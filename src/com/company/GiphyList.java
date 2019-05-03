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

public class GiphyList {


    private ReadWriteLock rwlock = new ReentrantReadWriteLock();
    public ArrayList<GiphyModel> models;
    JSONParser parser = new JSONParser();
    private long nextAvailableId = 0;
    public Gson gson;

    static GiphyList sGiphhyList;

    public static GiphyList getInstance() {
        if(sGiphhyList == null) sGiphhyList = new GiphyList();
        return sGiphhyList;
    }

    private GiphyList() {
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
        models.add(model);
        //if write was successful
        if(writeList()) {
            //Add to list and return true
            System.out.println("Adding to models: ");
            return true;
        }
        else {
            models.remove(model);
            return false;
        }
    }

    public boolean update(String json) {
        GiphyModel model = gson.fromJson(json, GiphyModel.class);
        getList(); //ensure list is not null
        System.out.println("Updating model with id: " + model.id);
        for(int i = 0; i < models.size(); i++) {
            if(model.id == models.get(i).id) {
                models.remove(i);
                models.add(i, model);
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
        for(int i = 0; i < models.size(); i++) {
            if(models.get(i).id == id) {
                models.remove(i);
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
            file.write(gson.toJson(models));
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

    private ArrayList<GiphyModel> getList() {

        if(models != null) {
            return models;
        }

        rwlock.readLock().lock();

        try {
            System.out.println("Reading json file");
            JSONArray a = (JSONArray) parser.parse(new FileReader("data.json"));
            models = new ArrayList<>();
            a.forEach(obj -> {
                GiphyModel model = new GiphyModel((JSONObject)obj);
                if(model.id > nextAvailableId) {
                    nextAvailableId = model.id;
                }
                nextAvailableId++; //Increment the next available ID
                models.add(model);
            });

        } catch (IOException e) {
            System.out.println("IOEception: " + e);
        } catch (ParseException e) {
            System.out.println("Parse exception: " + e);
        }

        finally {
            rwlock.readLock().unlock();
        }

        return models;
    }


}

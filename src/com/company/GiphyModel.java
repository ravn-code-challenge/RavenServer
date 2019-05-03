package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class GiphyModel {
    long id;
    String type;
    String title;
    String src;
    String author;
    Date date;
    long viewCount;
    long viewOrder;

    public GiphyModel(JSONObject object) {
        id = (long)object.get("id");
        type = (String)object.get("type");
        title = (String)object.get("title");
        src = (String)object.get("src");
        author = (String)object.get("author");
        date = new Date((long)object.get("date"));
        viewCount = (long)object.get("viewCount");
        viewOrder = (long)object.get("viewOrder");
    }


    public ArrayList<GiphyModel> createGipjhyList(JSONArray array) {
        ArrayList<GiphyModel> giphyList = new ArrayList<>();
        for(int i = 0; i < array.size(); i++) {
            giphyList.add(new GiphyModel((JSONObject) array.get(i)));
        }
        return giphyList;
    }


}

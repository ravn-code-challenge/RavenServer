package com.company;

import com.google.gson.annotations.SerializedName;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class GiphyModel {

    public static final String ID_STRING = "id";
    public static final String TYPE_STRING = "type";
    public static final String TITLE_STRING = "title";
    public static final String SRC_STRING = "src";
    public static final String AUTHOR_STRING = "author";
    public static final String DATE_STRING = "date";
    public static final String VIEW_COUNT_STRING = "viewCount";

    @SerializedName(ID_STRING)
    public long id;

    @SerializedName(TYPE_STRING)
    public String type;

    @SerializedName(TITLE_STRING)
    public String title;

    @SerializedName(SRC_STRING)
    public String src;

    @SerializedName(AUTHOR_STRING)
    public String author;

    @SerializedName(DATE_STRING)
    public Date date;

    @SerializedName(VIEW_COUNT_STRING)
    public long viewCount;

    public long viewOrder;


}

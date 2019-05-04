package com.company.model;

import java.util.ArrayList;

import static com.company.model.GiphyModel.*;

public class GiphyList {


    private static final String ASCENDING_STRING = "ascending";
    private static final String DESCENDING_STRING = "descending";

    public enum SortType {
        ASCENDING,
        DESCENDING
    }

    public enum SortField {
        ID,
        TYPE,
        TITLE,
        SRC,
        AUTHOR,
        DATE,
        VIEW_COUNT,
    }

    public String sortField;
    public String sortType;
    public ArrayList<GiphyModel> models;


    public void setSortType(SortType newSortType) {
        switch (newSortType) {
            case ASCENDING:
                sortType = ASCENDING_STRING;
                break;
            case DESCENDING:
                sortType = DESCENDING_STRING;
                break;
        }
    }

    public SortType getSortType() {
        if(sortType.toLowerCase().equals(ASCENDING_STRING)) {
            return SortType.ASCENDING;
        }
        else {
            return SortType.DESCENDING;
        }
    }

    public void setSortField(SortField newSortField) {
        switch (newSortField) {
            case ID:
                sortField = ID_STRING;
                break;
            case TYPE:
                sortField = TYPE_STRING;
                break;
            case SRC:
                sortField = SRC_STRING;
                break;
            case TITLE:
                sortField = TITLE_STRING;
                break;
            case AUTHOR:
                sortField = AUTHOR_STRING;
                break;
            case DATE:
                sortField = DATE_STRING;
                break;
            case VIEW_COUNT:
                sortField = VIEW_COUNT_STRING;
                break;
        }
    }


    public SortField getSortField(String sortField) {
        String sortFieldLower = sortField.toLowerCase();
        if(sortFieldLower.equals(ID_STRING)) {
            return SortField.ID;
        }
        else if(sortFieldLower.equals(TYPE_STRING)) {
            return SortField.TYPE;
        }
        else if(sortFieldLower.equals(TITLE_STRING)) {
            return SortField.TITLE;
        }
        else if(sortFieldLower.equals(SRC_STRING)) {
            return SortField.SRC;
        }
        else if(sortFieldLower.equals(AUTHOR_STRING)) {
            return SortField.AUTHOR;
        }
        else if(sortFieldLower.equals(DATE_STRING)) {
            return SortField.DATE;
        }
        else if(sortFieldLower.equals(VIEW_COUNT_STRING)) {
            return SortField.VIEW_COUNT;
        }
        else {
            return SortField.ID;
        }
    }

}
package com.company.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.company.model.GiphyModel.*;

public class GiphyList {


    public static final String ID_STRING = "id";
    public static final String TYPE_STRING = "type";
    public static final String TITLE_STRING = "title";
    public static final String SRC_STRING = "src";
    public static final String AUTHOR_STRING = "author";
    public static final String DATE_STRING = "date";
    public static final String VIEW_COUNT_STRING = "viewCount";

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

    public String setSortField(SortField newSortField) {
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
        return null;
    }


    public SortField getSortField() {
        if(sortField.equals(ID_STRING)) {
            return SortField.ID;
        }
        else if(sortField.equals(TYPE_STRING)) {
            return SortField.TYPE;
        }
        else if(sortField.equals(TITLE_STRING)) {
            return SortField.TITLE;
        }
        else if(sortField.equals(SRC_STRING)) {
            return SortField.SRC;
        }
        else if(sortField.equals(AUTHOR_STRING)) {
            return SortField.AUTHOR;
        }
        else if(sortField.equals(DATE_STRING)) {
            return SortField.DATE;
        }
        else if(sortField.equals(VIEW_COUNT_STRING)) {
            return SortField.VIEW_COUNT;
        }
        else {
            return SortField.ID;
        }
    }

    public void sortList() {
        Comparator<GiphyModel> comparator;
        switch (getSortField()) {
            case ID:
                if(getSortType() == SortType.ASCENDING) {
                    System.out.println("Sorting using ascending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            if(o1.id == o2.id) return 0;
                            if(o1.id > o2.id) return 1;
                            return -1;
                        }
                    };
                }
                else {
                    System.out.println("Sorting using descending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            if(o1.id == o2.id) return 0;
                            if(o1.id > o2.id) return -1;
                            return 1;
                        }
                    };
                }
                models.sort(comparator);
                break;
            case TYPE:
                if(getSortType() == SortType.ASCENDING) {
                    System.out.println("Sorting using ascending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o1.type.toLowerCase().compareTo(o2.type.toLowerCase());
                        }
                    };
                }
                else {
                    System.out.println("Sorting using descending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o2.type.toLowerCase().compareTo(o1.type.toLowerCase());
                        }
                    };
                }
                models.sort(comparator);
                break;
            case SRC:
                if(getSortType() == SortType.ASCENDING) {
                    System.out.println("Sorting using ascending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o1.src.toLowerCase().compareTo(o2.src.toLowerCase());
                        }
                    };
                }
                else {
                    System.out.println("Sorting using descending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o2.src.toLowerCase().compareTo(o1.src.toLowerCase());
                        }
                    };
                }
                models.sort(comparator);
                break;
            case TITLE:
                if(getSortType() == SortType.ASCENDING) {
                    System.out.println("Sorting using ascending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o1.title.toLowerCase().compareTo(o2.title.toLowerCase());
                        }
                    };
                }
                else {
                    System.out.println("Sorting using descending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o2.title.toLowerCase().compareTo(o1.title.toLowerCase());
                        }
                    };
                }
                models.sort(comparator);
                break;
            case AUTHOR:
                if(getSortType() == SortType.ASCENDING) {
                    System.out.println("Sorting using ascending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o1.author.toLowerCase().compareTo(o2.author.toLowerCase());
                        }
                    };
                }
                else {
                    System.out.println("Sorting using descending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o2.author.toLowerCase().compareTo(o1.author.toLowerCase());
                        }
                    };
                }
                models.sort(comparator);
                break;
            case DATE:
                if(getSortType() == SortType.ASCENDING) {
                    System.out.println("Sorting using ascending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o1.date.compareTo(o2.date);
                        }
                    };
                }
                else {
                    System.out.println("Sorting using descending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            return o2.date.compareTo(o1.date);
                        }
                    };
                }
                models.sort(comparator);
                break;
            case VIEW_COUNT:
                if(getSortType() == SortType.ASCENDING) {
                    System.out.println("Sorting using ascending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            if(o1.viewCount == o2.viewCount) return 0;
                            if(o1.viewCount > o2.viewCount) return 1;
                            return -1;
                        }
                    };
                }
                else {
                    System.out.println("Sorting using descending");
                    comparator = new Comparator<GiphyModel>() {
                        @Override
                        public int compare(GiphyModel o1, GiphyModel o2) {
                            if(o1.viewCount == o2.viewCount) return 0;
                            if(o1.viewCount > o2.viewCount) return -1;
                            return 1;
                        }
                    };
                }
                models.sort(comparator);
                break;
        }
    }

}
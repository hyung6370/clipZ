package com.example.clipz2.Class;

import java.util.ArrayList;

public class SingleItem_tv {
    private String id;
    private String name;
    private String original_name;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String first_air_date;
    private String vote_average;
    private ArrayList<Integer> genre_ids = new ArrayList<>();

    public SingleItem_tv(String id, String name, String original_name, String poster_path, String overview, String backdrop_path, String first_air_date, String vote_average, ArrayList<Integer> genre_ids) {
        this.id = id;
        this.name = name;
        this.original_name = original_name;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.first_air_date = first_air_date;
        this.vote_average = vote_average;
        this.genre_ids = genre_ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }
}

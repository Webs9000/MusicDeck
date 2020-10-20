package com.websmobileapps.musicdeck.Model;


import java.util.Calendar;

public class Card {
    private String title;
    private String artist;
    private int listRank;
    private Object albumArt;
    private Calendar publicationDate;
    private int rating;
    private String favTrack;

    //Do nothing constructor, if using getter/setters
    public Card() {

    }
    //Everything required constructor
    public Card(String title, String artist, int listRank, Object albumArt, Calendar publicationDate) {
        this.title = title;
        this.artist = artist;
        this.listRank = listRank;
        this.albumArt = albumArt;
        this.publicationDate = publicationDate;
    }
    //Everything constructor
    public Card(String title, String artist, int listRank, Object albumArt, Calendar publicationDate, int rating, String favTrack){
        this.title = title;
        this.artist = artist;
        this.albumArt = albumArt;
        this.publicationDate = publicationDate;
        this.rating = rating;
        this.favTrack = favTrack;
    }

}

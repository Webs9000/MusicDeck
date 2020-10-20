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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getListRank() {
        return listRank;
    }

    public void setListRank(int listRank) {
        this.listRank = listRank;
    }

    public Object getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Object albumArt) {
        this.albumArt = albumArt;
    }

    public Calendar getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFavTrack() {
        return favTrack;
    }

    public void setFavTrack(String favTrack) {
        this.favTrack = favTrack;
    }
}

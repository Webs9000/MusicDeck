package com.websmobileapps.musicdeck.Model;

public class Card {

    private String title;
    private String artist;
    private int listRank;
    private String artURL;
    private String publicationDate;
    private int rating;
    private String favTrack;

    //Do nothing constructor, required for firebase
    public Card() {
    }

    //Everything required constructor
    public Card(String title, String artist, int listRank, String artURL, String publicationDate) {
        this.title = title;
        this.artist = artist;
        this.listRank = listRank;
        this.artURL = artURL;
        this.publicationDate = publicationDate;
    }

    //Everything constructor
    public Card(String title, String artist, int listRank, String artURL, String publicationDate, int rating, String favTrack){
        this.title = title;
        this.artist = artist;
        this.listRank = listRank;
        this.artURL = artURL;
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

    public String getArtURL() {
        return artURL;
    }

    public void setArtURL(String artURL) {
        this.artURL = artURL;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
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

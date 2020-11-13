package com.websmobileapps.musicdeck.Model;


public class Deck {

    private String creator;
    private String title;
    private String subject;

    public Deck(){
    }

    public Deck(String creator, String title, String subject) {
        this.creator = creator;
        this.title = title;
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

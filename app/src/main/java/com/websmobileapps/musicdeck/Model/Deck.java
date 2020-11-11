package com.websmobileapps.musicdeck.Model;

import java.util.ArrayList;

public class Deck {

    private String creator;
    private String title;
    private String subject;
    private final ArrayList<Card> cards;

    //One card starter, no title/subject yet
    public Deck(String title, String subject, String creator) {
        this.title = title;
        this.subject = subject;
        this.creator = creator;
        this.cards = new ArrayList<>();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }
}

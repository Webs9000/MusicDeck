package com.websmobileapps.musicdeck.Model;

import java.util.List;

public class Deck {

    private String creatorUsername;
    private String title;
    private String subject;
    private List<Card> listOfCards;

    //One card starter, no title/subject yet
    public Deck(String title, String subject, String creator) {
        this.title = title;
        this.subject = subject;
        this.creatorUsername = creator;
    }

    public void addCard(Card newCard) {
        listOfCards.add(newCard);
    }

    public void removeCard(Card card) {
        listOfCards.remove(card);
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

    public String getCreatorUsername() { return creatorUsername; }
}

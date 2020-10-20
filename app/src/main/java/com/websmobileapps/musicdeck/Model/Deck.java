package com.websmobileapps.musicdeck.Model;

import java.util.List;

public class Deck {

    private String title;
    private String subject;
    private List<Card> listOfCards;

    //One card starter, no title/subject yet
    public Deck(Card initialCard) {
        this.addCard(initialCard);
    }
    public Deck(Card initialCard, String title, String subject) {
        this.addCard(initialCard);
        this.title = title;
        this.subject = subject;
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
}

package com.websmobileapps.musicdeck.Model;

import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;

public class DatabaseUser {

    private String username;
    private List<Deck> deckList;

    // Required for Firebase
    public DatabaseUser() {}

    public DatabaseUser(String username) {
        this.username = username;
        this.deckList = new LinkedList<>();
    }

    public static void addDeck(DatabaseUser user, Deck deck) {
        user.getDeckList().add(deck);
    }

    public List<Deck> getDeckList() {
        return deckList;
    }

    public String getUsername() {
        return username;
    }
}

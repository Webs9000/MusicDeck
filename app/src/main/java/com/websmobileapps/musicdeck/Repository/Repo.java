package com.websmobileapps.musicdeck.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.websmobileapps.musicdeck.Model.DatabaseUser;
import com.websmobileapps.musicdeck.Model.Deck;

import java.util.ArrayList;
import java.util.Objects;

public class Repo {

    static Repo instance;
    static DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    String currentDeckUID;
    String currentDeckTitle;
    String currentDeckCreator;

    public static Repo getInstance() {

        if (instance == null) {
            instance = new Repo();
        }

        return instance;
    }

    // Add a new user to the database
    public Task<Void> addUser(String uid, String username) {
        DatabaseUser newUser = new DatabaseUser(username);
        return mReference.child("users").child(uid).setValue(newUser);
    }

    // Retrieve user's decks

    // Retrieve reference to a user
    public DatabaseReference getRootRef() {
        return mReference;
    }

    public DatabaseReference getUserRef(String uid) {
        return mReference.child("users").child(uid);
    }

    // Retrieve reference to all the decks
    public DatabaseReference getDecksRef() {
        return mReference.child("decks");
    }

    //delete a user
    public void deleteUser(String uid) {
        mReference.child("users").child(uid).removeValue();
    }

    // Add a new Deck

    // Retrieve reference for a particular deck's Cards.  This case takes 3 methods.
    public DatabaseReference getCards(String deckUID) {
        return mReference.child("decks").child(deckUID).child("cards");
    }

    public void setCurrentDeck(String deckUID, String title, String creator) {
        currentDeckUID = deckUID;
        currentDeckTitle = title;
        currentDeckCreator = creator;
    }

    public String getCurrentDeckUID() {
        return currentDeckUID;
    }

    public String getCurrentDeckTitle() {
        return currentDeckTitle;
    }

    public String getCurrentDeckCreator() {
        return currentDeckCreator;
    }

    // Update a Deck
}

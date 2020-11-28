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

import de.umass.lastfm.Album;

/*  Singleton model class for handling Firebase Realtime Database references.
 */
public class Repo {

    static Repo instance;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private String currentDeckUID;
    private String currentDeckTitle;
    private String currentDeckCreator;
    private Album currentAlbum;

    private Repo() {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.setPersistenceEnabled(true);
        mReference = mDatabase.getReference();
    }

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
    public DatabaseReference getUserDecks(String UID) {
        return mReference.child("users").child(UID).child("decks");
    }

    // Retrieve root reference for data fan out.  This is used to add and update decks.
    public DatabaseReference getRootRef() {
        return mReference;
    }

    // Get reference to a user
    public DatabaseReference getUserRef(String uid) {
        return mReference.child("users").child(uid);
    }

    // Retrieve reference to all the decks
    public DatabaseReference getDecksRef() {
        return mReference.child("decks");
    }

    // Retrieve a ref to a particular deck
    public DatabaseReference getDeckRef(String UID) {
        return mReference.child("decks").child(UID);
    }

    //delete a user
    public void deleteUser(String uid) {
        mReference.child("users").child(uid).removeValue();
    }

    // Retrieve reference for a particular deck's Cards.
    public DatabaseReference getCards(String deckUID) {
        return mReference.child("decks").child(deckUID).child("cards");
    }

    // Cache information for a currently loaded deck to be passed to a view.  This is
    // mostly used for creating a new deck.
    public void setCurrentDeck(String deckUID, String title, String creator) {
        currentDeckUID = deckUID;
        currentDeckTitle = title;
        currentDeckCreator = creator;
    }

    // Retrieve UID for the currently loaded deck.  These
    public String getCurrentDeckUID() {
        return currentDeckUID;
    }

    // Retrieve title for the currently loaded deck
    public String getCurrentDeckTitle() {
        return currentDeckTitle;
    }

    // Retrieve creator for the currently loaded deck
    public String getCurrentDeckCreator() {
        return currentDeckCreator;
    }

    // Cache an Album object to be loaded by the new card view.
    public void setAlbum(Album album) {
        currentAlbum = album;
    }

    // Retrieve the currently loaded Album object.
    public Album getCurrentAlbum() {
        return currentAlbum;
    }
}

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
    private final ArrayList<Deck> decks = new ArrayList<>();
    static DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private final MutableLiveData<ArrayList<Deck>> deckMLD = new MutableLiveData<>();
    private final MutableLiveData<String> usernameMLD = new MutableLiveData<>();

    public static Repo getInstance() {

        if (instance == null) {
            instance = new Repo();
        }

        return instance;
    }

    public MutableLiveData<ArrayList<Deck>> getDecks() {

        if (decks.size() == 0) {
            loadDecks();
        }

        deckMLD.setValue(decks);

        return deckMLD;
    }

    private void loadDecks() {

        Query query = mReference.child("decks");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot shot : snapshot.getChildren()) {
                    decks.add(shot.getValue(Deck.class));
                }
                deckMLD.postValue(decks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // Add a new user to the database
    public Task<Void> addUser(String uid, String username) {
        DatabaseUser newUser = new DatabaseUser(username);
        return mReference.child("users").child(uid).setValue(newUser);
    }

    // Retrieve user's decks

    // Retrieve user's username
    public DatabaseReference getUserRef(String uid) {
        return mReference.child("users").child(uid);
    }

    public MutableLiveData<String> getUsername(final String uid) {
        mReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    DatabaseUser user = shot.getValue(DatabaseUser.class);
                    if (Objects.requireNonNull(shot.getKey()).equals(uid)) {
                        usernameMLD.setValue(Objects.requireNonNull(user).getUsername());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return usernameMLD;
    }

    //delete a user
    public void deleteUser(String uid) {
        mReference.child("users").child(uid).removeValue();
    }

    // Add a new Deck

    // Retrieve a Deck

    // Update a Deck
}

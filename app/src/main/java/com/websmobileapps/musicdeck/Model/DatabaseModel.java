package com.websmobileapps.musicdeck.Model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DatabaseModel {
    Application mApplication;

    private DatabaseReference mReference;

    private MutableLiveData<Deck> mDeckMutableLiveData;
    private MutableLiveData<String> mUsernameMutableLiveData;

    public DatabaseModel(Application application) {
        mApplication = application;

        mReference = FirebaseDatabase.getInstance().getReference();

        mDeckMutableLiveData = new MutableLiveData<>();
        mUsernameMutableLiveData = new MutableLiveData<>();
    }

    // Add a new user
    public Task<Void> addUser(FirebaseUser user, String username) {
        String uid = user.getUid();
        return mReference.child("users").child(uid).child("username").setValue(username)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(mApplication, "Failed to add user to DB: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Retrieve user's decks
    public Query getUserDecks(String uid) {
        Query query = mReference.child("users").child(uid).child("decks");
        return query;
    }

    // Retrieve user's username
    public String getUsername(String uid) {
        mReference.child("users").child(uid).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsernameMutableLiveData.postValue(Objects.requireNonNull(snapshot.getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mApplication, "Failed to retrieve username: " + error.toException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return mUsernameMutableLiveData.getValue();
    }

    // Add a new Deck
    public void addDeck(Deck deck, String userUID) {
        String uid = mReference.child("decks").push().getKey();
        mReference.child("decks").child(uid).setValue(deck);
        mReference.child("users").child(userUID).child("decks").child(uid).setValue(deck);
    }

    // Retrieve a Deck
    public MutableLiveData<Deck> getDeckMutableLiveData() {
        return mDeckMutableLiveData;
    }

    // Update a Deck
}

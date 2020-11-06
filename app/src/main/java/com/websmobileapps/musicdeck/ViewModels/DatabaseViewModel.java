package com.websmobileapps.musicdeck.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.websmobileapps.musicdeck.Model.DatabaseModel;
import com.websmobileapps.musicdeck.Model.Deck;

public class DatabaseViewModel extends AndroidViewModel {
    private DatabaseModel mDatabaseModel;

    private MutableLiveData<Deck> mDeckMutableLiveData;

    public DatabaseViewModel(@NonNull Application application) {
        super(application);

        mDatabaseModel = new DatabaseModel(application);
        mDeckMutableLiveData = mDatabaseModel.getDeckMutableLiveData();
    }

    // Add a new user
    public Task<Void> addUser(FirebaseUser user, String username) {
        return mDatabaseModel.addUser(user, username);
    }

    // Retrieve user's decks
    public Query getUserDecks(String uid) {
        return mDatabaseModel.getUserDecks(uid);
    }

    // Retrieve user's username

    // Add a new Deck

    // Retrieve a Deck
    public MutableLiveData<Deck> getDeckMutableLiveData() {
        return mDeckMutableLiveData;
    }

    // Update a Deck
}

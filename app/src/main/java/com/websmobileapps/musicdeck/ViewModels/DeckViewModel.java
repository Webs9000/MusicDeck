package com.websmobileapps.musicdeck.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.websmobileapps.musicdeck.Model.Deck;
import com.websmobileapps.musicdeck.Repository.Repo;

import java.util.ArrayList;

public class DeckViewModel extends ViewModel {

    MutableLiveData<ArrayList<Deck>> decks;

    public void init() {

        if (decks != null) {
            return;
        }

        decks = Repo.getInstance().getDecks();
    }

    public MutableLiveData<ArrayList<Deck>> getDecks() {
        return decks;
    }
}

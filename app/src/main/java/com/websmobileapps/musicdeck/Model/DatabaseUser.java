package com.websmobileapps.musicdeck.Model;

import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;

public class DatabaseUser {

    private String username;

    // Required for Firebase
    public DatabaseUser() {}

    public DatabaseUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

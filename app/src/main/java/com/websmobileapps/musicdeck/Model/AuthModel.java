package com.websmobileapps.musicdeck.Model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AuthModel {

    static AuthModel instance;
    private static FirebaseAuth mFirebaseAuth;
    private static MutableLiveData<FirebaseUser> mUserMutableLiveData;

    public static AuthModel getInstance() {

        if (instance == null) {
            instance = new AuthModel();
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserMutableLiveData = new MutableLiveData<>();

        if (mFirebaseAuth.getCurrentUser() != null) {
            mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
        }

        return instance;
    }

    public Task<AuthResult> register(String email, String password) {
        return  mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                    }
                });
    }

    public Task<AuthResult> login(String email, String password) {
        return mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                    }
                });
    }

    public void logout() {
        mFirebaseAuth.signOut();
    }

    public void deleteUser(){
        Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).delete();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }
}

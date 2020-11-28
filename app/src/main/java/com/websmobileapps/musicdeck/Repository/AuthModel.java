package com.websmobileapps.musicdeck.Repository;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/*  Singleton model class for referencing and storing Firebase authentication information
 */
public class AuthModel {

    static AuthModel instance;
    private static FirebaseAuth mFirebaseAuth;
    private static MutableLiveData<FirebaseUser> mUserMutableLiveData;

    private AuthModel() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserMutableLiveData = new MutableLiveData<>();

        if (mFirebaseAuth.getCurrentUser() != null) {
            mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
        }
    }

    public static AuthModel getInstance() {
        if (instance == null) {
            instance = new AuthModel();
        }

        return instance;
    }

    // Register a new user
    public Task<AuthResult> register(String email, String password) {
        return  mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                    }
                });
    }

    // Login an existing user
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
        mUserMutableLiveData.setValue(null);
    }

    public void deleteUser(){
        Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).delete();
        mFirebaseAuth.signOut();
        mUserMutableLiveData.setValue(null);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }
}

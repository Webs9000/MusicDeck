package com.websmobileapps.musicdeck.Model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AuthModel {
    private Application mApplication;
    private FirebaseAuth mFirebaseAuth;
    private MutableLiveData<FirebaseUser> mUserMutableLiveData;

    public AuthModel(Application application) {
        mApplication = application;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserMutableLiveData = new MutableLiveData<>();

        if (mFirebaseAuth.getCurrentUser() != null) {
            mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
        }
    }

    public Task<AuthResult> register(String email, String password) {
        return  mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(mApplication, "Registration failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public Task<AuthResult> login(String email, String password) {
        return mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(mApplication, "Login failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logout() {
        mFirebaseAuth.signOut();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }
}

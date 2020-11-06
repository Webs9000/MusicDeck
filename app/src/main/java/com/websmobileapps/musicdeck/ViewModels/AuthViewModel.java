package com.websmobileapps.musicdeck.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.websmobileapps.musicdeck.Model.AuthModel;

public class AuthViewModel extends AndroidViewModel {
    private AuthModel mAuthModel;
    private MutableLiveData<FirebaseUser> mUserMutableLiveData;

    public AuthViewModel(@NonNull Application application) {
        super(application);

        mAuthModel = new AuthModel(application);
        mUserMutableLiveData = mAuthModel.getUserMutableLiveData();
    }

    public Task<AuthResult> register(String email, String password) {
        return mAuthModel.register(email, password);
    }

    public Task<AuthResult> login(String email, String password) {
        return mAuthModel.login(email, password);
    }

    public void logout() {
        mAuthModel.logout();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }
}

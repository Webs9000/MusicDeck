package com.websmobileapps.musicdeck.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.websmobileapps.musicdeck.Repository.AuthModel;
import com.websmobileapps.musicdeck.Repository.Repo;

import java.util.Objects;

public class AuthViewModel extends ViewModel {
    private MutableLiveData<FirebaseUser> mUserMutableLiveData;
    private MutableLiveData<String> mUsernameMLD;
    private AuthModel mAuthModel;

    public void init() {

        if (mUserMutableLiveData != null) {
            return;
        }

        mAuthModel = AuthModel.getInstance();
        mUserMutableLiveData = mAuthModel.getUserMutableLiveData();
    }

    public Task<Void> register(String email, String password, final String username) {
        Task<AuthResult> authRegister = mAuthModel.register(email, password);

        return authRegister.continueWithTask(new Continuation<AuthResult, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<AuthResult> task) throws Exception {
                return Repo.getInstance().addUser(Objects.requireNonNull(mAuthModel.getUserMutableLiveData().getValue()).getUid(), username);
            }
        });
    }

    public Task<AuthResult> login(String email, String password) {
        return mAuthModel.login(email, password);
    }

    public void logout() {
        mAuthModel.logout();
    }

    public void deleteUser() {
        String uid = Objects.requireNonNull(mAuthModel.getUserMutableLiveData().getValue()).getUid();
        mAuthModel.deleteUser();
        Repo.getInstance().deleteUser(uid);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }
}

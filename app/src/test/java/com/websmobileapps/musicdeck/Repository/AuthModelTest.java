package com.websmobileapps.musicdeck.Repository;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import junit.framework.TestCase;

import java.util.Objects;

public class AuthModelTest extends TestCase {

    public void testGetInstance() {
    }

    public void testRegister() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> testResult = firebaseAuth.createUserWithEmailAndPassword("unitTest@test.org", "testing1");
        AuthModel liveAuthModel = AuthModel.getInstance();
        Task<AuthResult> liveResult = liveAuthModel.register("unitTest@test.org", "testing1");
        assertEquals(testResult, liveResult);
    }

    public void testLogin() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> testResult = firebaseAuth.signInWithEmailAndPassword("unitTest@test.org", "testing1");
        AuthModel liveAuthModel = AuthModel.getInstance();
        Task<AuthResult> liveResult = liveAuthModel.login("unitTest@test.org", "testing1");
        assertEquals(testResult, liveResult);
    }

    //Nothing to test here without having the MutableLiveData object to check
    public void testLogout() {

    }

    //Same as above really, hard to test without having the MutableLiveData values, since the actual method has no return
    public void testDeleteUser() {

    }

    public void testGetUserMutableLiveData() {

    }
}
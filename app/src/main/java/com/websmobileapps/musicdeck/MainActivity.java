package com.websmobileapps.musicdeck;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.websmobileapps.musicdeck.Fragments.LoginFragment;
import com.websmobileapps.musicdeck.Fragments.SignUpFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    BottomNavigationView mBottomNavigationView;
    SignUpFragment mSignUpFragment;
    LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() called");

        initializeObjects();
        replaceFragments(mSignUpFragment);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.itemSignUp:
                        replaceFragments(mSignUpFragment);
                        return true;
                    case R.id.itemLogin:
                        replaceFragments(mLoginFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void replaceFragments(Fragment fragment) {
        try {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.fragment_container, fragment).commit();
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

    }
    
    private void initializeObjects() {
        try {
            mBottomNavigationView = findViewById(R.id.BNV);
            mSignUpFragment = new SignUpFragment();
            mLoginFragment = new LoginFragment();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}

package com.websmobileapps.musicdeck;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.websmobileapps.musicdeck.Fragments.LoginFragment;
import com.websmobileapps.musicdeck.Fragments.SignUpFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bnv = findViewById(R.id.BNV);
        bnv.setVisibility(View.VISIBLE);

        final ImageButton menu_button = findViewById(R.id.menu_button);
        menu_button.setVisibility(View.GONE);

        final NavController  navCon = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment);

        NavigationUI.setupWithNavController(bnv, navCon);

        navCon.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.signUpFragment || destination.getId() == R.id.loginFragment) {
                    bnv.setVisibility(View.VISIBLE);
                    menu_button.setVisibility(View.GONE);
                } else {
                    bnv.setVisibility(View.GONE);
                    menu_button.setVisibility(View.VISIBLE);
                }
            }
        });

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navCon.navigate(R.id.sidebarFragment);
            }
        });
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

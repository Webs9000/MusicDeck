package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.Objects;

/**
 * This fragment is for logging in existing users.  Upon success, they are sent to the home view.
 * (NO HOME VIEW YET)
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private View mLoginFragment;

    private AuthViewModel mAuthViewModel;

    private EditText mEmailET, mPassET;
    private Button mLoginButton;
    private ProgressBar mLoginProgBar;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        mAuthViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called");

        mLoginFragment = inflater.inflate(R.layout.fragment_login, container, false);
        attachToXML();
        return mLoginFragment;
    }

    // Initialize all the various variables and views
    private void attachToXML() {
        Log.d(TAG, "attachToXML() called");
        try {

            mEmailET = mLoginFragment.findViewById(R.id.loginEmailET);
            mPassET = mLoginFragment.findViewById(R.id.loginPassET);
            mLoginProgBar = mLoginFragment.findViewById(R.id.loginProgBar);

            mLoginButton = mLoginFragment.findViewById(R.id.loginButton);
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginUser();
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Logs in the user through FirebaseAuth
    private void loginUser() {
        mAuthViewModel.logout();
        Log.d(TAG, "loginUser() called");
        try {
            String email = mEmailET.getText().toString();
            String pass = mPassET.getText().toString();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out the log in form!", Toast.LENGTH_SHORT).show();
            } else {
                mLoginProgBar.setVisibility(View.VISIBLE);
                mLoginButton.setEnabled(false);

                mAuthViewModel.login(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mLoginProgBar.setVisibility(View.INVISIBLE);
                            mLoginButton.setEnabled(true);

                            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeViewFragment);
                        } else {
                            Toast.makeText(requireContext(), "Login failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            mLoginProgBar.setVisibility(View.INVISIBLE);
                            mLoginButton.setEnabled(true);
                        }
                    }
                });
            }
        }
        catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
    }
}
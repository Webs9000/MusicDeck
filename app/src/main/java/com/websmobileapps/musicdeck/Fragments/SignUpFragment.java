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
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.Objects;

/**
 * This fragment is for signing up new users via Firebase auth.  The user is also added to our
 * Firebase realtime database at the same time for tracking of their deck list.  Upon success,
 * they are sent to the home view (NOT YET).
 */
public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";
    View mSignUpFragment;
    private EditText mUserEmailET, mUserPasswordET, mUsernameET;
    private Button mSignUpButton;
    private ProgressBar mProgressBar;

    private AuthViewModel mAuthViewModel;

    public SignUpFragment() {
        // Required empty public constructor
    }

    // Creates a new Firebase User.  That user is also added to our Firebase database.
    public void createUser() {
        Log.d(TAG, "createUser() called");
        try {
            final String username = mUsernameET.getText().toString();
            String userEmail = mUserEmailET.getText().toString();
            String userPassword = mUserPasswordET.getText().toString();

            // Check form has been filled out
            if (username.isEmpty() ||  userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out the sign up form!", Toast.LENGTH_SHORT).show();
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                mSignUpButton.setEnabled(false);

                // Attempt to add user to both Firebase Auth and DB
                mAuthViewModel.register(userEmail, userPassword, username).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "User Created!", Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mSignUpButton.setEnabled(true);
                            mUsernameET.setText("");
                            mUserEmailET.setText("");
                            mUserPasswordET.setText("");

                            Navigation.findNavController(requireView()).navigate(R.id.action_signUpFragment_to_createDeckFragment);
                        } else {
                            Toast.makeText(requireContext(), "Sign up failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mSignUpButton.setEnabled(true);
                        }
                    }
                });
            }
        }
        catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // Initialize all the various variables and views
    private void attachToXML() {
        Log.d(TAG, "attachToXML() called");
        try {

            mUsernameET = mSignUpFragment.findViewById(R.id.signupUsernameET);
            mUserEmailET = mSignUpFragment.findViewById(R.id.signupEmailET);
            mUserPasswordET = mSignUpFragment.findViewById(R.id.signupPassET);
            mProgressBar = mSignUpFragment.findViewById(R.id.signUpProgBar);

            mSignUpButton = mSignUpFragment.findViewById(R.id.signUpButton);
            mSignUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = mUsernameET.getText().toString();
                    String userEmail = mUserEmailET.getText().toString();
                    String userPassword = mUserPasswordET.getText().toString();

                    if (username.isEmpty() ||  userEmail.isEmpty() || userPassword.isEmpty()) {
                        Toast.makeText(getContext(), "Please fill out the sign up form!", Toast.LENGTH_SHORT).show();
                    } else {
                        createUser();
                    }
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

        mSignUpFragment = inflater.inflate(R.layout.fragment_sign_up, container, false);
        attachToXML();

        return mSignUpFragment;
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
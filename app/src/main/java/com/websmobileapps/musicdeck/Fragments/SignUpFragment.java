package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.websmobileapps.musicdeck.Model.DatabaseUser;
import com.websmobileapps.musicdeck.Model.Deck;
import com.websmobileapps.musicdeck.R;

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

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mRootNode;
    private DatabaseReference mReference;

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

            // Add to firebase auth
            if (username.isEmpty() ||  userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out the sign up form!", Toast.LENGTH_SHORT).show();
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                mSignUpButton.setEnabled(false);

                mFirebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getContext(), "User Created!", Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.INVISIBLE);
                                mSignUpButton.setEnabled(true);
                                mUsernameET.setText("");
                                mUserEmailET.setText("");
                                mUserPasswordET.setText("");

                                // Add to Firebase database
                                String uid = Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getUid();
                                DatabaseUser newUser = new DatabaseUser(username);
                                mReference.child(uid).setValue(newUser);
                                Toast.makeText(getContext(), "User added to DB!", Toast.LENGTH_SHORT).show();

                                Toast.makeText(getContext(), "Launch home view!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.INVISIBLE);
                                mSignUpButton.setEnabled(true);
                            }
                        });

            }
        }
        catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // Checks is the user entered email is already in use.
    private void checkUserExists() {
        Log.d(TAG, "checkUserExists() called");
        try {
            String email = mUserEmailET.getText().toString();
            if (mFirebaseAuth != null && !email.isEmpty()) {
                mProgressBar.setVisibility(View.VISIBLE);
                mSignUpButton.setEnabled(false);

                mFirebaseAuth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                boolean noExist = task.getResult().getSignInMethods().isEmpty();

                                if (noExist) {
                                    createUser();
                                } else {
                                    Toast.makeText(getContext(), "Email already in use.", Toast.LENGTH_SHORT).show();
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    mSignUpButton.setEnabled(true);
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                mSignUpButton.setEnabled(true);
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

            mFirebaseAuth = FirebaseAuth.getInstance();
            mRootNode = FirebaseDatabase.getInstance();
            mReference = mRootNode.getReference().child("users");

            mSignUpButton = mSignUpFragment.findViewById(R.id.signUpButton);
            mSignUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkUserExists();
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
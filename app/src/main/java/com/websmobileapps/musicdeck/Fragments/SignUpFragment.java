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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.websmobileapps.musicdeck.R;

public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";
    View mSignUpFragment;
    private EditText mUserEmailET, mUserPasswordET;
    private Button mSignUpButton;

    private FirebaseAuth mFirebaseAuth;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public void createUser() {
        try {
            String userEmail = mUserEmailET.getText().toString();
            String userPassword = mUserPasswordET.getText().toString();
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out the sign up form!", Toast.LENGTH_SHORT).show();
            } else {
                mFirebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getContext(), "User Created!", Toast.LENGTH_SHORT).show();
                                if (mFirebaseAuth.getCurrentUser() != null) {
                                    mFirebaseAuth.signOut();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void attachToXML() {
        try {
            mUserEmailET = mSignUpFragment.findViewById(R.id.signupEmailET);
            mUserPasswordET = mSignUpFragment.findViewById(R.id.signupPassET);

            mFirebaseAuth = FirebaseAuth.getInstance();

            mSignUpButton = mSignUpFragment.findViewById(R.id.signUpButton);
            mSignUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createUser();
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
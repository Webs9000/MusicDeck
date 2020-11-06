package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.websmobileapps.musicdeck.Model.Deck;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.Objects;

/**
 * Fragment to create new lists
 */
public class CreateListFragment extends Fragment {

    private static final String TAG = "CreateListFragment";
    View mCreateListFragment;
    private EditText mListTitle, mListDesc;
    private Button mCreateButton;

    private FirebaseDatabase mRootNode;
    private DatabaseReference mReference;

    private AuthViewModel mAuthViewModel;
    private FirebaseUser mCurrentUser;

    public CreateListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
    }

    private void attachToXML() {
        try {
            mListTitle = mCreateListFragment.findViewById(R.id.titleET);
            mListDesc = mCreateListFragment.findViewById(R.id.descET);

            // Get database refs
            mRootNode = FirebaseDatabase.getInstance();
            mReference = mRootNode.getReference().child("decks");

            mCurrentUser = mAuthViewModel.getUserMutableLiveData().getValue();

            mCreateButton = mCreateListFragment.findViewById(R.id.createButton);
            mCreateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Create a deck.  NOT RIGHT YET.
                    String uid = mReference.push().getKey();
                    String title = mListTitle.getText().toString();
                    String desc = mListDesc.getText().toString();
                    Deck newDeck = new Deck(title, desc, mCurrentUser.getUid());
                    mReference.child(Objects.requireNonNull(uid)).setValue(newDeck);

                    // Update the current user's database entry with the new deck
                    mReference = mRootNode.getReference("users");
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
        // Inflate the layout for this fragment
        mCreateListFragment = inflater.inflate(R.layout.fragment_create_list, container, false);
        attachToXML();
        return mCreateListFragment;
    }
}
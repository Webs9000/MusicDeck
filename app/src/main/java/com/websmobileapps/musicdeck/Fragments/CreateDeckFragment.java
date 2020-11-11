package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.websmobileapps.musicdeck.Model.DatabaseUser;
import com.websmobileapps.musicdeck.Model.Deck;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.Objects;

/**
 * Fragment to create new lists
 */
public class CreateDeckFragment extends Fragment {

    private static final String TAG = "CreateDeckFragment";
    View mCreateListFragment;
    private EditText mListTitle, mListDesc;
    private Button mCreateButton;

    private FirebaseDatabase mRootNode;
    private DatabaseReference mReference;

    private AuthViewModel mAuthViewModel;
    private FirebaseUser mCurrentUser;

    public CreateDeckFragment() {
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

                    // Create a deck.
                    final String uid = mReference.push().getKey();
                    final String title = mListTitle.getText().toString();
                    final String subject = mListDesc.getText().toString();

                    final String userUID = Objects.requireNonNull(mAuthViewModel.getUserMutableLiveData().getValue()).getUid();
                    Repo.getInstance().getUserRef(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            DatabaseUser user = snapshot.getValue(DatabaseUser.class);
                            String username = Objects.requireNonNull(user).getUsername();
                            Deck newDeck = new Deck(title, subject, username);
                            mReference.child(Objects.requireNonNull(uid)).setValue(newDeck)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(requireContext(),"Deck created!", Toast.LENGTH_SHORT).show();
                                                mListTitle.setText("");
                                                mListDesc.setText("");
                                            } else {
                                                Toast.makeText(requireContext(), "Deck create failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

//                    Deck newDeck = new Deck(title, subject, userUID);
//                    mReference.child(Objects.requireNonNull(uid)).setValue(newDeck)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(requireContext(),"Deck created!", Toast.LENGTH_SHORT).show();
//                                        mListTitle.setText("");
//                                        mListDesc.setText("");
//                                    } else {
//                                        Toast.makeText(requireContext(), "Deck create failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });

                    // Update the current user's database entry with the new deck
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
        mCreateListFragment = inflater.inflate(R.layout.fragment_create_deck, container, false);
        attachToXML();
        return mCreateListFragment;
    }
}
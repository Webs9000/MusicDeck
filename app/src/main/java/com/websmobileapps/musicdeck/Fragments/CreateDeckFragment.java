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
import com.google.firebase.database.ValueEventListener;
import com.websmobileapps.musicdeck.Model.DatabaseUser;
import com.websmobileapps.musicdeck.Model.Deck;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Fragment to create new lists
 */
public class CreateDeckFragment extends Fragment {

    private static final String TAG = "CreateDeckFragment";
    View mCreateListFragment;
    private EditText mListTitle, mListDesc;
    private Button mCreateButton;
    private DatabaseReference mReference;
    private Repo mRepo;

    private AuthViewModel mAuthViewModel;

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
            mRepo = Repo.getInstance();
            mReference = mRepo.getRootRef();

            mCreateButton = mCreateListFragment.findViewById(R.id.createButton);
            mCreateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Create a deck.
                    final String uid = mReference.child("decks").push().getKey();
                    final String title = mListTitle.getText().toString();
                    final String subject = mListDesc.getText().toString();
                    final String userUID = Objects.requireNonNull(mAuthViewModel.getUserMutableLiveData().getValue()).getUid();
                    mRepo.getUserRef(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            DatabaseUser user = snapshot.getValue(DatabaseUser.class);
                            String username = Objects.requireNonNull(user).getUsername();
                            Deck newDeck = new Deck(username, title, subject);

                            Map<String, Object> fanOutObject = new HashMap<>();
                            fanOutObject.put("/decks/" + uid, newDeck);
                            fanOutObject.put("/users/" + userUID + "/decks/" + uid, newDeck);

                            mReference.updateChildren(fanOutObject)
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

                            mRepo.setCurrentDeck(uid, title, username);

                            // Navigate to deck edit view
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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
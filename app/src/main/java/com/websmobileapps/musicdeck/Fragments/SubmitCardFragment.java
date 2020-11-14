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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.websmobileapps.musicdeck.Model.Card;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

public class SubmitCardFragment extends Fragment {

    private AuthViewModel mAuthViewModel;
    private Spinner mySpinner;
    private RatingBar mRatingBar;
    private Button mSubmitButton;
    private EditText listRankInput;
    View mSubmitCardFragment;

    public SubmitCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
    }

    private void attachToXML() {
        try {
            // The attachments in the XML
            // The spinner for favorite track selection
            mySpinner = mSubmitCardFragment.findViewById(R.id.trackOptions);
            // The star selector
            mRatingBar = mSubmitCardFragment.findViewById(R.id.ratingBar);
            // The list rank
            listRankInput = mSubmitCardFragment.findViewById(R.id.listRankNum);

            mSubmitButton = mSubmitCardFragment.findViewById(R.id.submitButton);
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // The place to put it in the list
                    String rank = listRankInput.getText().toString();

                    if (rank.isEmpty()) {
                        Toast.makeText(getContext(), "Please enter a list rank.", Toast.LENGTH_SHORT).show();
                    } else {
                        int listRank = 0;
                        try {
                            listRank = Integer.parseInt(rank);
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Please enter a sane number for the list rank.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Instantiate the Repo
                        Repo r = Repo.getInstance();
                        // Grab the current deck from the repo
                        final String deck = r.getCurrentDeckUID();
                        // Grab the user ID
                        final String userUID = Objects.requireNonNull(mAuthViewModel.getUserMutableLiveData().getValue()).getUid();
                        // Grab all the album details
                        Album a = r.getCurrentAlbum();
                        final String name = a.getName();
                        final String artist = a.getArtist();
                        final String albumArt = a.getImageURL(ImageSize.SMALL);
                        // Get the fav track selection from the user
                        String favTrack = "";
                        if (mySpinner.getSelectedItem() != null) {
                            favTrack = mySpinner.getSelectedItem().toString();
                        }
                        // Get the number of stars from the user
                        final int rating = (int) mRatingBar.getRating();
                        // The publication date
                        Date releaseDate = a.getReleaseDate();
                        final String publicationDate = releaseDate.toString();

                        // Form the card object
                        Card c = new Card(name, artist, listRank, albumArt, publicationDate, rating, favTrack);

                        // Write to the database
//                        DatabaseReference ref = Repo.getInstance().getRootRef();
//                        Map<String, Object> fanOutObject = new HashMap<>();
//                        fanOutObject.put("/decks/" + uid, newDeck);
//                        fanOutObject.put("/users/" + userUID + "/decks/" + uid, newDeck);
//
//                        mReference.updateChildren(fanOutObject)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(requireContext(),"Deck created!", Toast.LENGTH_SHORT).show();
//                                            mListTitle.setText("");
//                                            mListDesc.setText("");
//                                        } else {
//                                            Toast.makeText(requireContext(), "Deck create failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });


                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
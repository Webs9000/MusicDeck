package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.websmobileapps.musicdeck.Model.Card;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;

public class SubmitCardFragment extends Fragment {

    private AuthViewModel mAuthViewModel;
    private Spinner mySpinner;
    private RatingBar mRatingBar;
    private Button mSubmitButton;
    private EditText mListRankInput;
    private View mSubmitCardFragment;

    public SubmitCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        mAuthViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mSubmitCardFragment = inflater.inflate(R.layout.fragment_add_card, container, false);
        attachToXML();
        return mSubmitCardFragment;
    }

    private void attachToXML() {
        try {
            // The attachments in the XML
            // The spinner for favorite track selection
            mySpinner = mSubmitCardFragment.findViewById(R.id.trackOptions);
            // The star selector
            mRatingBar = mSubmitCardFragment.findViewById(R.id.ratingBar);
            // The list rank
            mListRankInput = mSubmitCardFragment.findViewById(R.id.listRankNum);

            // Get the repo
            Repo r = Repo.getInstance();
            // Determine if we need to get the album from LastFM again
            final Album a;
            if(r.isEditingCard()) {
                // We need to make a new request
                // The key
                final String API_Key = r.getLastFMKey();
                // Make the request based on the name and artist of the album
                String searchTerm = r.getCurrentCardTitle();
                Collection<Album> results = Album.search(searchTerm, API_Key);
                // Grab the first result
                a = results.iterator().next();
                // Update the repo
            } else {
                a = r.getCurrentAlbum();
            }

            // Populate the spinner
            List<String> spinnerArray =  new ArrayList<String>();
            // Get ready to populate the spinner
            Collection<Track> tracks = a.getTracks();
            for (Track track : tracks) {
                spinnerArray.add(track.toString());
            }
            // Get an adapter for the spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Attach to the spinner
            mySpinner.setAdapter(adapter);

            mSubmitButton = mSubmitCardFragment.findViewById(R.id.submitButton);
            // Wait for the submit button to be clicked
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // The place to put it in the list
                    String rank = mListRankInput.getText().toString();

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

                        // Get the repo
                        Repo r = Repo.getInstance();
                        // Grab the current deck from the repo
                        final String deckUID = r.getCurrentDeckUID();
                        // Grab the user ID
                        final String userUID = Objects.requireNonNull(mAuthViewModel.getUserMutableLiveData().getValue()).getUid();
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
                        Card newCard = new Card(name, artist, listRank, albumArt, publicationDate, rating, favTrack);

                        // Write to the database
                        DatabaseReference ref = Repo.getInstance().getRootRef();
                        String cardUID = ref.child("decks").child(deckUID).child("cards").push().getKey();
                        Map<String, Object> fanOutObject = new HashMap<>();
                        fanOutObject.put("/decks/" + deckUID + "/cards/" + cardUID, newCard);
                        fanOutObject.put("/users/" + userUID + "/decks/" + deckUID + "/cards/" + cardUID, newCard);

                        ref.updateChildren(fanOutObject)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(requireContext(),"Card added!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(requireContext(), "Card add failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        // TODO: Check this works
                        Navigation.findNavController(requireView()).navigate(R.id.action_submitCardFragment_to_editDeckFragment);

                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.websmobileapps.musicdeck.R;
import java.util.Collection;
import de.umass.lastfm.Album;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Track;
import com.websmobileapps.musicdeck.Repository.Repo;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

public class AddCardFragment extends Fragment {

    private static final String TAG = "AddCardFragment";
    View mAddCardFragment;
    private EditText mAlbumSearchTerm;
    private AuthViewModel mAuthViewModel;

    public AddCardFragment() {
        // Empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void attachToXML() {
        try {
            // A function to perform the Last FM album search and populate with results
            // Get the search term
            mAlbumSearchTerm = mAddCardFragment.findViewById(R.id.AlbumSearchBar);
            // The key
            final String API_Key = "d01898c6753899df4878ce31bf1a4af1";
            // The search term from the text box
            final String searchTerm = mAlbumSearchTerm.getText().toString();
            // Set up the request
            Caller.getInstance().setUserAgent("Music Deck");
            // Make the request
            Collection<Album> results = Album.search(searchTerm, API_Key);

            // Go through all our results and add them to the page
            for(Album result : results) {
                // Grab the bits of interest
                String name = result.getName();
                String artist = result.getArtist();
                Collection<Track> tracks = result.getTracks();
                // Fill the recycler view

            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAddCardFragment = inflater.inflate(R.layout.fragment_add_card, container, false);
        attachToXML();
        return mAddCardFragment;
    }
}

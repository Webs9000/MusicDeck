package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
    private Button mSearchButton;

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
            // Get the search box
            mAlbumSearchTerm = mAddCardFragment.findViewById(R.id.AlbumSearchBar);
            // The search button
            mSearchButton = mAddCardFragment.findViewById(R.id.albumSearchButton);
            // Get the repo ready
            final Repo r = Repo.getInstance();
            // The key
            //final String API_Key = r.getLastFMKey();
            final String API_Key = "d01898c6753899df4878ce31bf1a4af1";
            // Wait for a button click
            mSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // The search term from the text box
                    final String searchTerm = mAlbumSearchTerm.getText().toString();
                    // Set up the request
                    //Caller.getInstance().setUserAgent("Music Deck");
                    // Make the request
                    Collection<Album> results = Album.search(searchTerm, API_Key);

                    // Simply return the first result
                    try {
                        // Grab the first result
                        Album result = results.iterator().next();
                        // Update the repo
                        r.setAlbum(result);
                        r.setIsEditingCard(Boolean.FALSE);
                        Navigation.findNavController(requireView()).navigate(R.id.action_addCardFragment_to_submitCardFragment);
                    } catch (Exception e) {
                        // We didn't find anything
                        Toast.makeText(getContext(), "ERROR: No results", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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

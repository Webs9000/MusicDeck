package com.websmobileapps.musicdeck.Fragments;

import android.os.AsyncTask;
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
import java.util.List;
import java.util.concurrent.Callable;

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
    private Collection<Album> mResult;
    private String searchTerm, API_Key;

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
            API_Key = r.getLastFMKey();
            // Wait for a button click
            mSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // The search term from the text box
                    searchTerm = mAlbumSearchTerm.getText().toString();
                    // Set up the request
                    //Caller.getInstance().setUserAgent("Music Deck");
                    // Make the request
//                    Callable<Collection<Album>> albumCall = new Callable<Collection<Album>>() {
//                        @Override
//                        public Collection<Album> call() throws Exception {
//                            return Album.search(searchTerm, API_Key);
//                        }
//                    };

                    try {
                        albumSearch();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (!r.isEditingCard()) {
                        Navigation.findNavController(requireView()).navigate(R.id.action_addCardFragment_to_submitCardFragment);
                    }

//                    try {
//                        mResult = (List<Album>) albumCall.call();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    // Simply return the first result
//                    try {
//                        // Grab the first result
//                        Album result = mResult.get(0);
//                        // Update the repo
//                        r.setAlbum(result);
//                        r.setIsEditingCard(Boolean.FALSE);
//                        Navigation.findNavController(requireView()).navigate(R.id.action_addCardFragment_to_submitCardFragment);
//                    } catch (Exception e) {
//                        // We didn't find anything
//                        Toast.makeText(getContext(), "ERROR: No results", Toast.LENGTH_SHORT).show();
//                    }
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

    private void albumSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mResult = Album.search(searchTerm, API_Key);

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Repo.getInstance().setAlbum(mResult.iterator().next());
                        Repo.getInstance().setIsEditingCard(Boolean.FALSE);
                    }
                });
            }
        }).start();
    }
}

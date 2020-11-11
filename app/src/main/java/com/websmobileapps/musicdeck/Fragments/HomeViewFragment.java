package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.websmobileapps.musicdeck.Model.Deck;
import com.websmobileapps.musicdeck.Model.HomeAdapter;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.ViewModels.DeckViewModel;

import java.util.ArrayList;

/**
 * Fragment for the home view.  Latest Decks are displayed in a recycler view.
 */
public class HomeViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    private DeckViewModel mDeckViewModel;
    View mHomeViewFragment;

    public HomeViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDeckViewModel = new ViewModelProvider(requireActivity()).get(DeckViewModel.class);
        mDeckViewModel.init();
        mDeckViewModel.getDecks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Deck>>() {
            @Override
            public void onChanged(ArrayList<Deck> decks) {
                mHomeAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHomeViewFragment = inflater.inflate(R.layout.fragment_home_view, container, false);

        mRecyclerView = mHomeViewFragment.findViewById(R.id.homeRecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mHomeAdapter = new HomeAdapter(mDeckViewModel.getDecks().getValue());

        mRecyclerView.setAdapter(mHomeAdapter);

        return mHomeViewFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
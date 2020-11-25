package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.websmobileapps.musicdeck.Model.Deck;
import com.websmobileapps.musicdeck.RecyclerViewHolders.HomeViewHolder;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;

/**
 * Fragment for the home view.  Latest Decks are displayed in a recycler view.
 */
public class HomeViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View mHomeView;

    public HomeViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHomeView = inflater.inflate(R.layout.fragment_home_view, container, false);

        return mHomeView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = mHomeView.findViewById(R.id.homeRecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Deck> options =
                new FirebaseRecyclerOptions.Builder<Deck>()
                .setQuery(Repo.getInstance().getDecksRef(), Deck.class)
                .build();

        FirebaseRecyclerAdapter<Deck, HomeViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Deck, HomeViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull HomeViewHolder holder, final int position, @NonNull final Deck model) {

                        holder.setItem(model.getTitle(), model.getCreator());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String key = getRef(position).getKey();
                                Repo.getInstance().setCurrentDeck(key, model.getTitle(), model.getCreator());

                                Navigation.findNavController(requireView()).navigate(R.id.action_homeViewFragment_to_deckViewFragment);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_deck, parent, false);

                        return new HomeViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}
package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.websmobileapps.musicdeck.Model.DatabaseUser;
import com.websmobileapps.musicdeck.Model.Deck;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.RecyclerViewHolders.DeckViewHolder;
import com.websmobileapps.musicdeck.Repository.Repo;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.Objects;

/**
 * Fragment for the user profile view.  User's Decks are displayed in a recycler view.
 */
public class ProfileViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View mProfileView;
    private TextView mUserTV;
    private Button mNewDeckButton;

    private AuthViewModel mAuthViewModel;

    public ProfileViewFragment() {
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
        mProfileView = inflater.inflate(R.layout.fragment_profile_view, container, false);

        return mProfileView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUserTV = mProfileView.findViewById(R.id.current_user_TV);
        mNewDeckButton = mProfileView.findViewById(R.id.new_deck_button);
        mNewDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_profileViewFragment_to_createDeckFragment);
            }
        });

        final String userUID = Objects.requireNonNull(mAuthViewModel.getUserMutableLiveData().getValue()).getUid();
        Repo.getInstance().getUserRef(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseUser user = snapshot.getValue(DatabaseUser.class);
                String username = Objects.requireNonNull(user).getUsername();
                mUserTV.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView = mProfileView.findViewById(R.id.profileRecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Deck> options =
                new FirebaseRecyclerOptions.Builder<Deck>()
                        .setQuery(Repo.getInstance().getUserDecks(userUID), Deck.class)
                        .build();

        FirebaseRecyclerAdapter<Deck, DeckViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Deck, DeckViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull DeckViewHolder holder, final int position, @NonNull final Deck model) {

                        holder.setItem(model.getTitle(), model.getCreator());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String key = getRef(position).getKey();
                                Repo.getInstance().setCurrentDeck(key, model.getTitle(), model.getCreator());

                                Navigation.findNavController(requireView()).navigate(R.id.action_profileViewFragment_to_editDeckFragment);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_deck, parent, false);

                        return new DeckViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}
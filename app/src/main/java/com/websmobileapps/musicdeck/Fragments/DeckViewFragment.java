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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.websmobileapps.musicdeck.Model.Card;
import com.websmobileapps.musicdeck.RecyclerViewHolders.CardViewHolder;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;

/**
 * Fragment for viewing the Cards in a Deck in a recycler view.
 */
public class DeckViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View mDeckView;
    private Repo mRepo;
    private TextView mTitleTV, mCreatorTV;
    private ImageButton mShareIB;
    private Button mEditButton;
    private Button mAddCardButton;

    public DeckViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDeckView = inflater.inflate(R.layout.fragment_deck_view, container, false);

        return mDeckView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRepo = Repo.getInstance();

        mShareIB = mDeckView.findViewById(R.id.share_button);
        mShareIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_deckViewFragment_to_qrCodeDisplayFragment);
            }
        });

        mTitleTV = mDeckView.findViewById(R.id.deck_view_title_TV);
        mCreatorTV = mDeckView.findViewById(R.id.deck_view_creator_TV);

        mTitleTV.setText(mRepo.getCurrentDeckTitle());
        mCreatorTV.setText(mRepo.getCurrentDeckCreator());

        mRecyclerView = mDeckView.findViewById(R.id.deck_edit_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String deckUID = mRepo.getCurrentDeckUID();

        FirebaseRecyclerOptions<Card> options =
                new FirebaseRecyclerOptions.Builder<Card>()
                        .setQuery(mRepo.getCards(deckUID).orderByChild("listRank"), Card.class)
                        .build();

        FirebaseRecyclerAdapter<Card, CardViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Card, CardViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CardViewHolder holder, int position, @NonNull Card model) {

                        holder.setItem(model.getTitle(), model.getArtist(), model.getListRank(), model.getArtURL(), model.getPublicationDate(), model.getRating(), model.getFavTrack());

                    }

                    @NonNull
                    @Override
                    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_card, parent, false);

                        return new CardViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}
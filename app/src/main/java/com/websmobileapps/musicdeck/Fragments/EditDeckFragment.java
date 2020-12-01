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
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.websmobileapps.musicdeck.Model.Card;
import com.websmobileapps.musicdeck.RecyclerViewHolders.CardViewHolder;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

/**
 * Fragment for viewing the Cards in a Deck in a recycler view.
 */
public class EditDeckFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View mDeckEdit;
    private Repo mRepo;
    private TextView mTitleTV, mCreatorTV;
    private ImageButton mShareIB;
    private Button mAddCardButton;
    private Button mDeleteDeckButton;

    public EditDeckFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDeckEdit = inflater.inflate(R.layout.fragment_deck_edit, container, false);

        return mDeckEdit;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRepo = Repo.getInstance();

        mShareIB = mDeckEdit.findViewById(R.id.share_button);
        mShareIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_deckViewFragment_to_qrCodeDisplayFragment);
            }
        });

        mTitleTV = mDeckEdit.findViewById(R.id.deck_view_title_TV);
        mCreatorTV = mDeckEdit.findViewById(R.id.deck_view_creator_TV);

        mTitleTV.setText(mRepo.getCurrentDeckTitle());
        mCreatorTV.setText(mRepo.getCurrentDeckCreator());

        mRecyclerView = mDeckEdit.findViewById(R.id.deck_view_recycler);
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

        /*
        The bottom two buttons
         */
        // Add card
        mAddCardButton = mDeckEdit.findViewById(R.id.AddCardButton);
        mAddCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the repo to know we are adding a card, not editing
                mRepo.setIsEditingCard(Boolean.TRUE);
                // Go to the add card fragment
                Navigation.findNavController(requireView()).navigate(R.id.action_deckEditPlaceholder_to_addCardFragment);
            }
        });

        // The delete deck button
        mDeleteDeckButton = mDeckEdit.findViewById(R.id.DeleteDeckButton);
        mDeleteDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the deck from the database
                // TODO: Verify this is correct
                AuthViewModel mAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
                mAuthViewModel.init();
                String UserID = mAuthViewModel.getUserMutableLiveData().getValue().getUid();
                mRepo.deleteDeck(UserID ,mRepo.getCurrentDeckUID());

                // Navigate back home
                // TODO: Edit the nav graph to make this possible
                // Navigation.findNavController(requireView()).navigate(R.id.);
            }
        });

    }
}
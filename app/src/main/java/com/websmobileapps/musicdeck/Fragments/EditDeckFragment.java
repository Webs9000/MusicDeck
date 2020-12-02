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
import com.websmobileapps.musicdeck.Model.Card;
import com.websmobileapps.musicdeck.RecyclerViewHolders.CardEditViewHolder;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;
import com.websmobileapps.musicdeck.ViewModels.AuthViewModel;

import java.util.Objects;

/**
 * Fragment for viewing the Cards in a Deck in a recycler view.
 */
public class EditDeckFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View mDeckEdit;
    private Repo mRepo;
    private Button mShareIB, mAddCardButton, mDeleteDeckButton;
    private TextView mTitleTV;

    AuthViewModel mAuthViewModel;

    public EditDeckFragment() {
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
        mDeckEdit = inflater.inflate(R.layout.fragment_deck_edit, container, false);

        return mDeckEdit;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRepo = Repo.getInstance();

        mShareIB = mDeckEdit.findViewById(R.id.edit_share_button);
        mShareIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_editDeckFragment_to_qrCodeDisplayFragment);
            }
        });

        mTitleTV = mDeckEdit.findViewById(R.id.deck_edit_title_TV);
        mTitleTV.setText(mRepo.getCurrentDeckTitle());

        mRecyclerView = mDeckEdit.findViewById(R.id.deck_edit_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String deckUID = mRepo.getCurrentDeckUID();

        FirebaseRecyclerOptions<Card> options =
                new FirebaseRecyclerOptions.Builder<Card>()
                        .setQuery(Repo.getInstance().getCards(deckUID).orderByChild("listRank"), Card.class)
                        .build();

        FirebaseRecyclerAdapter<Card, CardEditViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Card, CardEditViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CardEditViewHolder holder, final int position, @NonNull final Card model) {

                        holder.setItem(model.getTitle(), model.getArtist(), model.getListRank(), model.getPublicationDate());

                        Button editCardButton = holder.itemView.findViewById(R.id.editCardButton);
                        editCardButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Set the repo to know we are editing a card, not adding
                                Repo repo = Repo.getInstance();
                                repo.setIsEditingCard(Boolean.TRUE);
                                repo.setCurrentCardTitle(model.getTitle());

                                Navigation.findNavController(requireView()).navigate(R.id.action_editDeckFragment_to_submitCardFragment);
                            }
                        });

                        Button deleteCardButton = holder.itemView.findViewById(R.id.deleteCardButton);
                        deleteCardButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String userUID = Objects.requireNonNull(mAuthViewModel.getUserMutableLiveData().getValue()).getUid();
                                String cardUID = getRef(position).getKey();
                                Repo.getInstance().deleteCard(userUID, cardUID);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CardEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_card_edit, parent, false);

                        return new CardEditViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

        /*
        The bottom two buttons
         */
        // Add card
        mAddCardButton = mDeckEdit.findViewById(R.id.addCardButton);
        mAddCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to the add card fragment
                Navigation.findNavController(requireView()).navigate(R.id.action_editDeckFragment_to_addCardFragment);
            }
        });

        // The delete deck button
        mDeleteDeckButton = mDeckEdit.findViewById(R.id.deleteDeckButton);
        mDeleteDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the deck from the database
                String UserID = Objects.requireNonNull(mAuthViewModel.getUserMutableLiveData().getValue()).getUid();
                mRepo.deleteCurrentDeck(UserID);

                Navigation.findNavController(requireView()).navigate(R.id.action_editDeckFragment_to_homeViewFragment);
            }
        });

    }
}
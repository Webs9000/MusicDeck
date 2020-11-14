package com.websmobileapps.musicdeck.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.websmobileapps.musicdeck.Model.Deck;
import com.websmobileapps.musicdeck.R;
import com.websmobileapps.musicdeck.Repository.Repo;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result rawResult) {
        Toast.makeText(getContext(), rawResult.getText(), Toast.LENGTH_SHORT).show();
        DatabaseReference dbRef = Repo.getInstance().getDeckRef(rawResult.getText());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Deck deck = snapshot.getValue(Deck.class);
                Repo.getInstance().setCurrentDeck(rawResult.getText(), deck.getTitle(), deck.getCreator());
                Navigation.findNavController(requireView()).navigate(R.id.action_qrCodeScannerFragment_to_deckViewFragment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

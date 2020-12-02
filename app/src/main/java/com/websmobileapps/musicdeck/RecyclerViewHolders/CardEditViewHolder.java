package com.websmobileapps.musicdeck.RecyclerViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websmobileapps.musicdeck.R;

public class CardEditViewHolder extends RecyclerView.ViewHolder {

    TextView rankTV, titleTV, artistTV, pubTV;

    public CardEditViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setItem(String title, String artist, int listRank, String publicationDate) {

        rankTV = itemView.findViewById(R.id.card_edit_item_rank_TV);
        titleTV = itemView.findViewById(R.id.card_edit_item_title_TV);
        artistTV = itemView.findViewById(R.id.card_edit_item_artist_TV);
        pubTV = itemView.findViewById(R.id.card_edit_item_pub_TV);

        String rank = "" + listRank;
        rankTV.setText(rank);
        titleTV.setText(title);
        artistTV.setText(artist);
        pubTV.setText(publicationDate);
    }
}
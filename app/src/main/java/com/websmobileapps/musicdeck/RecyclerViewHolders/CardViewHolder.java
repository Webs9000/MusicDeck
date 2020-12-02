package com.websmobileapps.musicdeck.RecyclerViewHolders;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.websmobileapps.musicdeck.R;

public class CardViewHolder extends RecyclerView.ViewHolder {

    ImageView albumArtIV;
    TextView rankTV, titleTV, artistTV, pubTV, favTV;
    RatingBar rateBar;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setItem(String title, String artist, int listRank, String artURL, String publicationDate, int rating, String favTrack) {

        albumArtIV = itemView.findViewById(R.id.card_item_art_IV);
        rankTV = itemView.findViewById(R.id.card_item_rank_TV);
        titleTV = itemView.findViewById(R.id.card_item_title_TV);
        artistTV = itemView.findViewById(R.id.card_item_artist_TV);
        pubTV = itemView.findViewById(R.id.card_item_pub_TV);
        favTV = itemView.findViewById(R.id.card_item_fav_TV);
        rateBar = itemView.findViewById(R.id.card_item_rate_bar);

        try {
            Glide.with(itemView)
                    .load(Uri.parse(artURL))
                    .placeholder(R.drawable.smaller_eat_it)
                    .into(albumArtIV);
        } catch (Exception e) {
            // Nothing to do here, default image will show
        }

        String rank = "" + listRank;
        rankTV.setText(rank);
        titleTV.setText(title);
        artistTV.setText(artist);
        pubTV.setText(publicationDate);

        if (favTrack.isEmpty()) {
            favTV.setText("");
        } else {
            favTV.setText(favTrack);
        }

        if (0 <= rating && rating <= 5) {
            rateBar.setVisibility(View.VISIBLE);
            rateBar.setRating(rating);
        } else {
            rateBar.setVisibility(View.GONE);
        }
    }
}

package com.websmobileapps.musicdeck.Model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.websmobileapps.musicdeck.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    TextView titleTV, creatorTV;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setitem(FragmentActivity activity, String creator, String title) {

        titleTV = itemView.findViewById(R.id.item_title);
        creatorTV = itemView.findViewById(R.id.item_creator);

        titleTV.setText(title);
        creatorTV.setText(creator);
    }
}

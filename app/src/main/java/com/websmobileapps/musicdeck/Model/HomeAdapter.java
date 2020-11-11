package com.websmobileapps.musicdeck.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websmobileapps.musicdeck.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<Deck> mDeckModels;

    public HomeAdapter(ArrayList<Deck> deckModels) {
        mDeckModels = deckModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setTag(mDeckModels.get(position));
        holder.title.setText(mDeckModels.get(position).getTitle());
        holder.creator.setText(mDeckModels.get(position).getCreator());

    }

    @Override
    public int getItemCount() {
        return mDeckModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView creator;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            creator = itemView.findViewById(R.id.item_creator);
        }
    }
}

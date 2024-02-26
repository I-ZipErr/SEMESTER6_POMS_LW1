package com.uni.lw1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    private static ArrayList<Note> note_list = new ArrayList<>();
    public static boolean addNote(Note note){
        return note_list.add(note);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = note_list.get(position);
        holder.noteName.setText(note.getName());
        holder.noteText.setText(note.getText());
        holder.noteModification.setText(note.getModification_date());
        holder.noteCreation.setText(note.getCreation_date());
        if(note.getIs_favorite())
        {
            holder.notFavorite.setVisibility(View.INVISIBLE);
            holder.favorite.setVisibility(View.VISIBLE);
        }
        else {
            holder.notFavorite.setVisibility(View.VISIBLE);
            holder.favorite.setVisibility(View.INVISIBLE);
        }
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.notFavorite.setVisibility(View.VISIBLE);
                holder.favorite.setVisibility(View.INVISIBLE);
                note_list.get(holder.getAdapterPosition()).setIs_favorite(false);
                DBOpenHelper.setIs_favorite(note_list.get(holder.getAdapterPosition()));
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        holder.notFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.notFavorite.setVisibility(View.INVISIBLE);
                holder.favorite.setVisibility(View.VISIBLE);
                note_list.get(holder.getAdapterPosition()).setIs_favorite(true);
                DBOpenHelper.setIs_favorite(note_list.get(holder.getAdapterPosition()));
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return note_list.size();
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
    private Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView noteText;
        private Button notFavorite;
        private Button favorite;
        private TextView noteName;
        private TextView noteModification;
        private TextView noteCreation;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            noteText = itemView.findViewById(R.id.noteText);
            noteName = itemView.findViewById(R.id.noteName);
            noteModification = itemView.findViewById(R.id.noteModification);
            noteCreation = itemView.findViewById(R.id.noteCreation);
            notFavorite = itemView.findViewById(R.id.notFavorite);
            favorite = itemView.findViewById(R.id.favorite);
        }


    }
}

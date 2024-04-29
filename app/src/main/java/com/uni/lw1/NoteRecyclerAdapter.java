package com.uni.lw1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> implements Filterable{

    private static ArrayList<Note> note_list = new ArrayList<>();
    public static void addNote(Note note){
        note_list.add(note);
    }
    public static Note getLastNote(){ return  note_list.get(note_list.size() - 1);}
    public static int getListLastIndex() {return note_list.size() - 1;}
    public static void updateNote(Note note){
        note_list.get(note_list.indexOf(note)).setAll(note);
    }
    public static void clearList(){
        note_list.clear();
    }
    public static void deleteNote(Note note){
        note_list.remove(note_list.get(note_list.indexOf(note)));
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
                DBOpenHelper.updateNote(note_list.get(holder.getAdapterPosition()));
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        holder.notFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.notFavorite.setVisibility(View.INVISIBLE);
                holder.favorite.setVisibility(View.VISIBLE);
                note_list.get(holder.getAdapterPosition()).setIs_favorite(true);
                DBOpenHelper.updateNote(note_list.get(holder.getAdapterPosition()));
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        NavDirections action = HomeFragmentDirections.HomeToNote(note_list.get(holder.getAdapterPosition()));
        holder.cardView.setOnClickListener(Navigation.createNavigateOnClickListener(action));
    }

    @Override
    public int getItemCount() {
        return note_list.size();
    }


    @Override
    public Filter getFilter() {
        return null;
    }

//    public Filter nameFilter() {
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String charString = constraint.toString().toLowerCase();
//                Log.e("PPPPPPPPA", "performFiltering: " + charString );
//                if (charString.isEmpty()) {
//                    searched_list = note_list;
//                } else {
//                    ArrayList<Note> filteredList = new ArrayList<>();
//                    for (Note note : note_list) {
//                        if (note.getName().toLowerCase().contains(charString) ||
//                            note.getText().toLowerCase().contains(charString)) {
//                            Log.e("PPPPPPPPSYA", "added: " + charString );
//                            filteredList.add(note);
//                        }
//                    }
//                    searched_list = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = searched_list;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                note_list = (ArrayList<Note>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//        return filter;
//    }

    public Filter categoryFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                ArrayList<Note> filteredList = new ArrayList<>();
                DBOpenHelper.fill_list();
                if(charString.length() == 0){
                    filteredList = note_list;
                }
                else switch (charString){
                    case "Избранные":
                        for (Note note : note_list)
                            if (note.getIs_favorite()) {
                                filteredList.add(note);
                            }
                        break;
                    case "Без группы":
                        for (Note note : note_list)
                            if (note.getGroup() == null || note.getGroup().isEmpty()) {
                                filteredList.add(note);
                            }
                        break;
                    default:
                        for (Note note : note_list)
                            if (note.getGroup() != null && note.getGroup().equals(charString)) {
                                filteredList.add(note);
                            }
                        break;
                }


                note_list = filteredList;
                FilterResults filterResults = new FilterResults();
                filterResults.values = note_list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                note_list = (ArrayList<Note>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

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

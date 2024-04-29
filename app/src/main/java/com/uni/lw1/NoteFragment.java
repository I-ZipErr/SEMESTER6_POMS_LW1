package com.uni.lw1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class NoteFragment extends Fragment {

    private static MaterialToolbar materialToolbar;
    private static Note note;

    private DialogFragment dialogFragment;
    private TextInputEditText editText;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = NoteFragmentArgs.fromBundle(getArguments()).getNoteInfo();
        dialogFragment = new NoteDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        materialToolbar = (MaterialToolbar)view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigateUp();
            }
        });

        editText = view.findViewById(R.id.note_text);

        editText.setText(note.getText());

        materialToolbar = (MaterialToolbar)view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigateUp();
            }
        });

        materialToolbar.setTitle(note.getName());
        materialToolbar = (MaterialToolbar)view.findViewById(R.id.materialToolbar);
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.delete) //switch-case since ADT 14 not working!!!
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Вы уверены, что хотите удалить заметку " + note.getName());
                    builder.setPositiveButton("Отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    builder.setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DBOpenHelper.deleteNote(note);
                            NoteRecyclerAdapter.deleteNote(note);
                            Navigation.findNavController(getView()).navigateUp();
                        }
                    });
                    builder.setCancelable(true);
                    builder.show();

                } else if (id == R.id.edit) {
                    note.setText(editText.getText().toString());
                    NavDirections action = NoteFragmentDirections.actionNoteFragmentToNoteDialogFragment(note);
                    Navigation.findNavController(view).navigate(action);
                }
                return true;
            }
        });

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        //fragment is visible
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("note", note);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        //note.setName(editName.getText().toString());
        note.setCurrentMod_date();
        note.setText(editText.getText().toString());
        try{
        NoteRecyclerAdapter.updateNote(note);
        DBOpenHelper.updateNote(note);}
        catch (Exception e){}
        super.onDestroyView();
    }


    public static void getNoteFromDialog(Note note){
        NoteFragment.note = note;
        materialToolbar.setTitle(note.getName());
    }

}
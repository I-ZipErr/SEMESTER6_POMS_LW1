package com.uni.lw1;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.DataInputStream;
import java.util.ArrayList;


public class NoteDialogFragment extends DialogFragment {


    public NoteDialogFragment() {
        // Required empty public constructor
    }
    private Spinner spinner;
    private TextInputEditText editText;
    private Button btnYes, btnNo;
    private Note note;

    public static NoteDialogFragment newInstance() {
        NoteDialogFragment fragment = new NoteDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = NoteFragmentArgs.fromBundle(getArguments()).getNoteInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_note_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        spinner = view.findViewById(R.id.category_spinner);
        editText= view.findViewById(R.id.note_name);
        btnNo = view.findViewById(R.id.btnNo);
        btnYes = view.findViewById(R.id.btnYes);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dismiss();}
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setName(editText.getText().toString());
                NoteFragment.getNoteFromDialog(note);
                dismiss();
            }
        });
//        ArrayList<Category> categories = new ArrayList<>();
//        categories.add(new Category( R.drawable.ic_no_group, "Без группы"));
//        categories.add(new Category(R.drawable.ic_work,"Работа"));
//        categories.add( new Category(R.drawable.ic_home,"Дом"));
//        categories.add(new Category(R.drawable.ic_family,"Семья"));
//        categories.add(new Category(R.drawable.ic_routine,"Повседневность"));
//        categories.add(new Category(R.drawable.ic_hobby,"Хобби"));
//        categories.add(new Category(R.drawable.ic_health,"Здоровье"));
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Без группы");
        categories.add("Работа");
        categories.add("Дом");
        categories.add("Семья");
        categories.add("Повседневность");
        categories.add("Хобби");
        categories.add("Здоровье");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        try{
        switch (note.getGroup()){
            case "Работа":
                spinner.setSelection(1);
                break;
            case "Дом":
                spinner.setSelection(2);
                break;
            case "Семья":
                spinner.setSelection(3);
                break;
            case "Повседневность":
                spinner.setSelection(4);
                break;
            case "Хобби":
                spinner.setSelection(5);
                break;
            case "Здоровье":
                spinner.setSelection(6);
                break;
            default:
                spinner.setSelection(0);
                break;
        }}
        catch (Exception e)
        {}

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                if(item.equals("Без группы")) note.setGroup("");
                else note.setGroup(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        editText.setText(note.getName());

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
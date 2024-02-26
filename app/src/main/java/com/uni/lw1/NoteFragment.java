package com.uni.lw1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;

public class NoteFragment extends Fragment {

    private MaterialToolbar materialToolbar;
    private Note note;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        //note = com.uni.lw1.HomeFragmentDirections.ActionHomeFragmentToNoteFragment.fromBu
        //bundle

    }

    int b = 8;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_note, container, false);
        //sdk
        //db reading
        //inflation

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //main activity
        //find view by id

        materialToolbar = (MaterialToolbar)view.findViewById(R.id.materialToolbar);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigateUp();
            }
        });

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.create) //switch-case since ADT 14 not working!!!
                {
                    //Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_noteFragment);
                } else if (id == R.id.search) {
                    Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_searchFragment);
                } else if (id == R.id.bin) {

                }
                return true;

            }
        });

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
    public void onDestroyView() {
        super.onDestroyView();
    }
}
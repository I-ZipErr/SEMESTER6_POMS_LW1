package com.uni.lw1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoteRecyclerAdapter noteRecyclerAdapter = new NoteRecyclerAdapter();
    private FloatingActionButton button;

 public HomeFragment() {
        super(R.layout.fragment_home);
    }

    private MaterialToolbar materialToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBOpenHelper.fill_list();
        noteRecyclerAdapter.categoryFilter().filter("");


        //bundle
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home, container, false);
        //sdk
        //inflation
        //no context!
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //main activity
        //find view by id
        //db
        button = (FloatingActionButton)view.findViewById(R.id.create_button);
        materialToolbar = (MaterialToolbar)view.findViewById(R.id.materialToolbar);
        drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        navigationView = (NavigationView)view.findViewById(R.id.navigationView);

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.all_item) {
                    noteRecyclerAdapter.categoryFilter().filter("");
                    return true;
                }else if (id == R.id.favorite_items) {
                    noteRecyclerAdapter.categoryFilter().filter("Избранные");
                    return true;
                }else if (id == R.id.no_group_items) {
                    noteRecyclerAdapter.categoryFilter().filter("Без группы");
                    return true;
                }else if (id == R.id.group_home) {
                    noteRecyclerAdapter.categoryFilter().filter("Дом");
                    return true;
                }else if (id == R.id.group_work) {
                    noteRecyclerAdapter.categoryFilter().filter("Работа");
                    return true;
                }else if (id == R.id.group_family) {
                    noteRecyclerAdapter.categoryFilter().filter("Семья");
                    return true;
                }else if (id == R.id.group_routine) {
                    noteRecyclerAdapter.categoryFilter().filter("Повседневность");
                    return true;
                }else if (id == R.id.group_hobby) {
                    noteRecyclerAdapter.categoryFilter().filter("Хобби");
                    return true;
                }else if (id == R.id.group_health) {
                    noteRecyclerAdapter.categoryFilter().filter("Здоровье");
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = DBOpenHelper.addNote();
                NoteRecyclerAdapter.addNote(note);
                NavDirections action = HomeFragmentDirections.HomeToNote(note);
                Navigation.findNavController(view).navigate(action);
            }
        });
        recyclerView.setAdapter(noteRecyclerAdapter);
        //searchRecyclerView.setAdapter(recyclerAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
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
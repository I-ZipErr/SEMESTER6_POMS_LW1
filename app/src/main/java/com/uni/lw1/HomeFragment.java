package com.uni.lw1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

//    private static SQLiteDatabase db;
//    private static DBOpenHelper dbOpenHelper;
 //   private static ContentValues contentValues;
//    private Cursor cursor;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter = new RecyclerAdapter();

 public HomeFragment() {
        super(R.layout.fragment_home);
    }

    private MaterialToolbar materialToolbar;
    private DrawerLayout drawerLayout;

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
        //bundle
    }

    int b = 8;
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





        materialToolbar = (MaterialToolbar)view.findViewById(R.id.materialToolbar);
        drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.create) //switch-case since ADT 14 not working!!! Can use when(item.getitemid()) case -> { return_value}
                {
                    //NavDirections action = com.uni.lw1.HomeFragmentDirections.
                    //HomeFragmentDirections.HomeToNote action = HomeFragment.
                    //Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_noteFragment);
                } else if (id == R.id.search) {
                    Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_searchFragment);
                } else if (id == R.id.bin) {

                }
                return true;

            }
        });

        DBOpenHelper.fill_list();

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(recyclerAdapter);

        //recyclerAdapter.notifyChanges();
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
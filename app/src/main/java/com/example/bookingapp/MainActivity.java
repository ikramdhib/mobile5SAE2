package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ExpandableListView expandableListView;
    private List<String> listGroupTitles;
    private Map<String, List<String>> listChildItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        expandableListView = findViewById(R.id.expandableListView);

        setupDrawerMenu();
        setupExpandableListView();

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupDrawerMenu() {
        listGroupTitles = new ArrayList<>();
        listChildItems = new HashMap<>();

        // Ajouter le groupe "Gestion des Hôtels"
        listGroupTitles.add("Gestion des Hôtels");

        // Ajouter les éléments enfants sous "Gestion des Hôtels"
        List<String> hotelOptions = new ArrayList<>();
        hotelOptions.add("Recherche Hôtel");

        listChildItems.put(listGroupTitles.get(0), hotelOptions);
    }

    private void setupExpandableListView() {
        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(this, listGroupTitles, listChildItems);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String selectedItem = listChildItems.get(listGroupTitles.get(groupPosition)).get(childPosition);
            if (selectedItem.equals("Recherche Hôtel")) {
                startActivity(new Intent(this, RechercheHotelActivity.class));
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}


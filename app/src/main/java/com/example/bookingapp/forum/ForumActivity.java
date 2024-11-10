package com.example.bookingapp.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView; // Import pour SearchView
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapter.DiscussionAdapter;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Discusion;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DiscussionAdapter adapter;
    private List<Discusion> currentDiscussionList;
    private List<Discusion> displayedDiscussionList; // Liste des discussions visibles
    private Button btnViewMore;
    private Button btnAddDiscussion;
    private AppDatabase db;
    private SearchView searchView; // Ajouter le SearchView
    private static final int DISCUSSION_LIMIT = 5; // Nombre initial de discussions visibles

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        // Initialiser RecyclerView, SearchView, et boutons
        recyclerView = findViewById(R.id.recycler_view_discussions);
        searchView = findViewById(R.id.search_view);
        btnViewMore = findViewById(R.id.btn_view_more);
        btnAddDiscussion = findViewById(R.id.btn_add_discussion);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentDiscussionList = new ArrayList<>();
        displayedDiscussionList = new ArrayList<>();

        // Initialiser la base de données
        db = AppDatabase.getAppDatabase(this);

        // Ajouter un utilisateur statique au démarrage de l'application
        //insertStaticUser();

        // Charger toutes les discussions depuis la base de données
        currentDiscussionList = db.discussionDao().getAllDiscussions();

        // Initialiser l'adaptateur avec la liste visible
        adapter = new DiscussionAdapter(this, displayedDiscussionList);
        recyclerView.setAdapter(adapter);

        // Charger les premières discussions visibles
        loadInitialDiscussions();

        // Définir la fonctionnalité de recherche
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterDiscussions(newText);
                return true;
            }
        });

        // Définir un clic listener pour le bouton "Ajouter une discussion"
        btnAddDiscussion.setOnClickListener(view -> {
            Intent intent = new Intent(ForumActivity.this, AddDiscussionActivity.class);
            startActivity(intent);
        });

        // Définir un clic listener pour le bouton "Voir Plus"
        btnViewMore.setOnClickListener(view -> loadMoreDiscussions());

        updateViewMoreButtonVisibility();
    }

    private void loadInitialDiscussions() {
        displayedDiscussionList.clear();
        int limit = Math.min(DISCUSSION_LIMIT, currentDiscussionList.size());
        for (int i = 0; i < limit; i++) {
            displayedDiscussionList.add(currentDiscussionList.get(i));
        }
        adapter.notifyDataSetChanged();
        updateViewMoreButtonVisibility();
    }

    private void loadMoreDiscussions() {
        int currentSize = displayedDiscussionList.size();
        int totalSize = currentDiscussionList.size();

        int newLimit = Math.min(currentSize + DISCUSSION_LIMIT, totalSize);
        for (int i = currentSize; i < newLimit; i++) {
            displayedDiscussionList.add(currentDiscussionList.get(i));
        }

        adapter.notifyDataSetChanged();
        updateViewMoreButtonVisibility();
    }

    private void updateViewMoreButtonVisibility() {
        if (displayedDiscussionList.size() < currentDiscussionList.size()) {
            btnViewMore.setVisibility(View.VISIBLE);
        } else {
            btnViewMore.setVisibility(View.GONE);
        }
    }

    private void filterDiscussions(String text) {
        displayedDiscussionList.clear();
        if (text.isEmpty()) {
            loadInitialDiscussions();
        } else {
            for (Discusion discussion : currentDiscussionList) {
                if (discussion.getTitle().toLowerCase().contains(text.toLowerCase()) ||
                        discussion.getContent().toLowerCase().contains(text.toLowerCase())) {
                    displayedDiscussionList.add(discussion);
                }
            }
        }
        adapter.notifyDataSetChanged();
        updateViewMoreButtonVisibility();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recharger les discussions après avoir ajouté une nouvelle discussion
        currentDiscussionList.clear();
        currentDiscussionList.addAll(db.discussionDao().getAllDiscussions());
        filterDiscussions(searchView.getQuery().toString());
    }

    /*private void insertStaticUser() {
        try {
            User staticUser = new User(
                    "Jane Doe",
                    "jane.doe@example.com",
                    "password123",
                    "987654321",
                    "user"
            );

            // Insérer l'utilisateur dans la base de données sans vérifier s'il existe déjà
            long userId = db.userDao().insertUser(staticUser);
            Log.d("DATABASE", "Utilisateur inséré manuellement avec ID : " + userId);

        } catch (Exception e) {
            Log.e("DATABASE", "Erreur lors de l'insertion de l'utilisateur", e);
        }
    }*/
}

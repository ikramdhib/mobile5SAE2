package com.example.bookingapp.forum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapter.CommentAdapter;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Comment;
import com.example.bookingapp.entity.Discusion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiscussionDetailActivity extends AppCompatActivity {

    private TextView discussionTitle, discussionContent, postDetails;
    private EditText editDiscussionTitle, editDiscussionContent, addCommentEditText;
    private Button updateButton, deleteButton, saveButton, addCommentButton;
    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private List<Comment> currentCommentList;
    private AppDatabase db;
    private int discussionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_detail);

        // Initialisation des vues
        discussionTitle = findViewById(R.id.discussion_title);
        discussionContent = findViewById(R.id.post_content);
        postDetails = findViewById(R.id.post_details);
        editDiscussionTitle = findViewById(R.id.edit_discussion_title);
        editDiscussionContent = findViewById(R.id.edit_discussion_content);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);
        saveButton = findViewById(R.id.save_button);
        addCommentEditText = findViewById(R.id.add_comment);
        addCommentButton = findViewById(R.id.submit_comment_button);
        recyclerViewComments = findViewById(R.id.recycler_view_comments);

        db = AppDatabase.getAppDatabase(this);

        // Récupérer les détails de la discussion depuis l'intent
        discussionId = getIntent().getIntExtra("discussionId", -1);
        String title = getIntent().getStringExtra("discussionTitle");
        String content = getIntent().getStringExtra("discussionContent");

        // Récupérer l'utilisateur actuel (exemple)
        String currentUser = "Jane Doe"; // Remplacez par la logique pour obtenir l'utilisateur connecté

        if (title != null) {
            discussionTitle.setText(title);
            editDiscussionTitle.setText(title);
        }

        if (content != null) {
            discussionContent.setText(content);
            editDiscussionContent.setText(content);
        }

        // Mettre à jour les détails de la publication
        String createdAt = new SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(new Date());
        String postDetailsText = "Auteur : " + currentUser + " • " + createdAt;
        postDetails.setText(postDetailsText);

        // Initialiser la liste des commentaires
        currentCommentList = new ArrayList<>();
        currentCommentList.addAll(db.commentDao().getCommentsByDiscussionId(discussionId));

        // Configurer le RecyclerView pour les commentaires
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser l'adaptateur
        commentAdapter = new CommentAdapter(this, currentCommentList, currentUser);
        recyclerViewComments.setAdapter(commentAdapter);

        // Logique de suppression
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (discussionId != -1) {
                    Discusion discussion = db.discussionDao().getDiscussionById(discussionId);
                    db.discussionDao().deleteDiscussion(discussion);
                    Toast.makeText(DiscussionDetailActivity.this, "Discussion supprimée", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        // Logique de mise à jour : Afficher les champs de modification
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discussionTitle.setVisibility(View.GONE);
                discussionContent.setVisibility(View.GONE);
                editDiscussionTitle.setVisibility(View.VISIBLE);
                editDiscussionContent.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                updateButton.setVisibility(View.GONE);
            }
        });

        // Logique de sauvegarde de la mise à jour
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (discussionId != -1) {
                    String updatedTitle = editDiscussionTitle.getText().toString().trim();
                    String updatedContent = editDiscussionContent.getText().toString().trim();

                    if (!updatedTitle.isEmpty() && !updatedContent.isEmpty()) {
                        Discusion discussion = db.discussionDao().getDiscussionById(discussionId);
                        discussion.setTitle(updatedTitle);
                        discussion.setContent(updatedContent);

                        db.discussionDao().updateDiscussion(discussion);

                        // Mise à jour des champs de texte
                        discussionTitle.setText(updatedTitle);
                        discussionContent.setText(updatedContent);

                        // Masquer les champs de modification et afficher les champs mis à jour
                        editDiscussionTitle.setVisibility(View.GONE);
                        editDiscussionContent.setVisibility(View.GONE);
                        saveButton.setVisibility(View.GONE);
                        discussionTitle.setVisibility(View.VISIBLE);
                        discussionContent.setVisibility(View.VISIBLE);
                        updateButton.setVisibility(View.VISIBLE);

                        Toast.makeText(DiscussionDetailActivity.this, "Discussion mise à jour avec succès", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DiscussionDetailActivity.this, "Les champs ne peuvent pas être vides", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Ajouter un nouveau commentaire
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = addCommentEditText.getText().toString().trim();
                if (!commentText.isEmpty()) {
                    // Créer un nouvel objet Comment
                    String createdAt = new SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(new Date());
                    Comment newComment = new Comment(commentText, createdAt, currentUser, discussionId);

                    // Insérer le commentaire dans la base de données
                    db.commentDao().insertComment(newComment);

                    // Ajouter le commentaire à la liste et mettre à jour l'adaptateur
                    currentCommentList.add(newComment);
                    commentAdapter.notifyItemInserted(currentCommentList.size() - 1);

                    // Réinitialiser le champ de texte
                    addCommentEditText.setText("");

                    Toast.makeText(DiscussionDetailActivity.this, "Commentaire ajouté", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DiscussionDetailActivity.this, "Le commentaire ne peut pas être vide", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

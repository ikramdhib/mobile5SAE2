package com.example.bookingapp.forum;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.R;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Discusion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddDiscussionActivity extends AppCompatActivity {

    private EditText discussionTitleInput;
    private EditText discussionInput;
    private Button submitButton;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);

        // Initialiser les champs de texte et le bouton
        discussionTitleInput = findViewById(R.id.discussion_title_input);
        discussionInput = findViewById(R.id.discussion_input);
        submitButton = findViewById(R.id.submit_button);
        db = AppDatabase.getAppDatabase(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = discussionTitleInput.getText().toString().trim();
                String content = discussionInput.getText().toString().trim();

                // Vérifiez si le titre et le contenu ne sont pas vides
                if (!title.isEmpty() && !content.isEmpty()) {
                    String createdAt = new SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(new Date());
                    int creatorId = 1;  // Identifiant utilisateur (modifiez ceci si nécessaire)

                    // Créer une nouvelle discussion avec le titre et le contenu fournis
                    Discusion newDiscussion = new Discusion(title, content, createdAt, creatorId);

                    // Insérer dans la base de données
                    long insertedId = db.discussionDao().insertDiscussion(newDiscussion);
                    Log.d("DATABASE", "Discussion insérée avec ID : " + insertedId);

                    Toast.makeText(AddDiscussionActivity.this, "Discussion ajoutée avec succès", Toast.LENGTH_SHORT).show();

                    // Finir l'activité et revenir au forum
                    finish();
                } else {
                    // Afficher un message d'erreur si les champs sont vides
                    Toast.makeText(AddDiscussionActivity.this, "Veuillez remplir le titre et le contenu de la discussion", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

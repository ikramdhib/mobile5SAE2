package com.example.bookingapp.forum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.R;

public class WriteCommentActivity extends AppCompatActivity {

    private EditText commentInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        commentInput = findViewById(R.id.comment_input);
        submitButton = findViewById(R.id.submit_button);

        // Ajouter un clic listener au bouton "Soumettre"
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = commentInput.getText().toString();
                if (!commentText.isEmpty()) {
                    // Enregistrez le commentaire ou effectuez une autre action (base de données, etc.)
                    Toast.makeText(WriteCommentActivity.this, "Commentaire soumis : " + commentText, Toast.LENGTH_SHORT).show();
                    finish(); // Retourne à la page précédente
                } else {
                    Toast.makeText(WriteCommentActivity.this, "Veuillez entrer un commentaire", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

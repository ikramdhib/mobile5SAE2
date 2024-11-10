package com.example.bookingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.entity.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;
    private Context context;
    private String currentUser;

    public CommentAdapter(Context context, List<Comment> commentList, String currentUser) {
        this.context = context;
        this.commentList = commentList;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);

        // Utiliser getAuthor() pour récupérer l'auteur du commentaire
        holder.usernameTextView.setText(comment.getAuthor());
        holder.contentTextView.setText(comment.getContent());
        holder.timestampTextView.setText(comment.getCreatedAt());

        // Si l'utilisateur actuel est l'auteur, rendre visible les boutons de suppression et de modification
        if (comment.getAuthor().equals(currentUser)) {
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);

            holder.editButton.setOnClickListener(v -> {
                // Logique pour éditer le commentaire
                holder.editContentEditText.setVisibility(View.VISIBLE);
                holder.editContentEditText.setText(comment.getContent());
                holder.contentTextView.setVisibility(View.GONE);
                holder.saveButton.setVisibility(View.VISIBLE);
            });

            holder.deleteButton.setOnClickListener(v -> {
                // Logique pour supprimer le commentaire
                commentList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, commentList.size());
            });

            holder.saveButton.setOnClickListener(v -> {
                String updatedContent = holder.editContentEditText.getText().toString().trim();
                if (!updatedContent.isEmpty()) {
                    comment.setContent(updatedContent);
                    holder.contentTextView.setText(updatedContent);

                    // Mettre à jour dans la base de données (si nécessaire)
                    // db.commentDao().updateComment(comment);

                    holder.editContentEditText.setVisibility(View.GONE);
                    holder.contentTextView.setVisibility(View.VISIBLE);
                    holder.saveButton.setVisibility(View.GONE);
                }
            });
        } else {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView usernameTextView;
        TextView contentTextView;
        TextView timestampTextView;
        ImageView editButton;
        ImageView deleteButton;
        EditText editContentEditText;
        Button saveButton;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.comment_username);
            contentTextView = itemView.findViewById(R.id.comment_content);
            timestampTextView = itemView.findViewById(R.id.comment_timestamp);
            editButton = itemView.findViewById(R.id.comment_edit_button);
            deleteButton = itemView.findViewById(R.id.comment_delete_button);
            editContentEditText = itemView.findViewById(R.id.edit_comment_content);
            saveButton = itemView.findViewById(R.id.comment_save_button);
        }
    }
}

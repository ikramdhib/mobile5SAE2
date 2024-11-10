package com.example.bookingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.forum.DiscussionDetailActivity;
import com.example.bookingapp.R;
import com.example.bookingapp.entity.Discusion;

import java.util.List;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder> {

    private Context context; // Contexte ajouté
    private List<Discusion> discussionList;

    public DiscussionAdapter(Context context, List<Discusion> discussionList) {
        this.context = context;
        this.discussionList = discussionList;
    }

    @NonNull
    @Override
    public DiscussionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discussion, parent, false);
        return new DiscussionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionViewHolder holder, int position) {
        Discusion discussion = discussionList.get(position);
        holder.title.setText(discussion.getTitle());
        holder.content.setText(discussion.getContent());
        holder.createdAt.setText(discussion.getCreatedAt());

        // Définir un clic sur chaque discussion pour ouvrir la page de détail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DiscussionDetailActivity.class);
            intent.putExtra("discussionId", discussion.getId());
            intent.putExtra("discussionTitle", discussion.getTitle());
            intent.putExtra("discussionContent", discussion.getContent());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return discussionList.size();
    }

    public static class DiscussionViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content, createdAt;  // Rendre les champs public

        public DiscussionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.discussion_title);
            content = itemView.findViewById(R.id.discussion_content);
            createdAt = itemView.findViewById(R.id.discussion_time);
        }
    }
}

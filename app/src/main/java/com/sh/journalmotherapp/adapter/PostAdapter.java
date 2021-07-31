package com.sh.journalmotherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sh.journalmotherapp.R;
import com.sh.journalmotherapp.model.PostModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final Context mContext;
    private final List<PostModel> listPostModels;
    private final OnPostItemClickListener listener;

    public PostAdapter(Context mContext, List<PostModel> listPostModels, OnPostItemClickListener listener) {
        this.mContext = mContext;
        this.listPostModels = listPostModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostModel model = listPostModels.get(position);

        holder.titleTextView.setText(model.getTitle());
        holder.detailsTextView.setText(model.getContent());

        Picasso.get().load(model.getImageUrl()).placeholder(R.drawable.ic_app_512)
                .error(R.drawable.ic_app_512).into(holder.postImageView);

        Picasso.get().load(model.getAuthor().getImageUrl()).placeholder(R.drawable.ic_app_256)
                .error(R.drawable.ic_app_256).into(holder.authorImageView);

        holder.dateTextView.setText(model.getCreatedDate());

        holder.bind(model, listener);
    }

    @Override
    public int getItemCount() {
        if (listPostModels != null) {
            return listPostModels.size();
        } else {
            return 0;
        }
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        protected TextView titleTextView, detailsTextView;
        protected ImageView postImageView;
        protected CircleImageView authorImageView;

        protected TextView dateTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
            postImageView = itemView.findViewById(R.id.postImageView);
            authorImageView = itemView.findViewById(R.id.authorImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }

        public void bind(final PostModel model, final OnPostItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onClickItem(model));
        }
    }

    public interface OnPostItemClickListener {
        void onClickItem(PostModel model);
    }

}
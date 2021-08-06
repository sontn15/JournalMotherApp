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
import com.sh.journalmotherapp.model.NewsEntity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private final Context mContext;
    private final List<NewsEntity> models;
    private final OnPostItemClickListener listener;

    public NewsAdapter(Context mContext, List<NewsEntity> models, OnPostItemClickListener listener) {
        this.mContext = mContext;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsEntity model = models.get(position);

        holder.titleTextView.setText(model.getTitle());
        holder.detailsTextView.setText(model.getContent());

        Picasso.get().load(model.getImageUrl()).placeholder(R.drawable.ic_stub)
                .error(R.drawable.ic_stub).into(holder.postImageView);

        holder.bind(model, listener);
    }

    @Override
    public int getItemCount() {
        if (models != null) {
            return models.size();
        } else {
            return 0;
        }
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        protected TextView titleTextView, detailsTextView;
        protected ImageView postImageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
            postImageView = itemView.findViewById(R.id.postImageView);
        }

        public void bind(final NewsEntity model, final OnPostItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onClickItem(model));
        }
    }

    public interface OnPostItemClickListener {
        void onClickItem(NewsEntity model);
    }

}